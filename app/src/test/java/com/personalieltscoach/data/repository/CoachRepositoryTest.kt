package com.personalieltscoach.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.WordSource
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.seed.Nce1WordPack
import com.personalieltscoach.data.seed.Nce2WordPack
import com.personalieltscoach.domain.model.PlacementResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate
import java.time.ZoneId

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CoachRepositoryTest {
    private lateinit var database: CoachDatabase
    private lateinit var repository: CoachRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CoachDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = CoachRepository(
            database = database,
            settingsRepository = object : SettingsProvider {
                override suspend fun current() = CoachSettings(
                    dailyNewWords = 10,
                    dailyReviewWords = 20,
                    dailySentences = 5
                )
            },
            json = Json { ignoreUnknownKeys = true }
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun newWordSessionStopsAtTwentyAndCanContinueWithAnotherBatch() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        assertEquals(20, repository.newWords().size)
        repeat(20) {
            val word = repository.newWords().first()
            repository.answerWord(word, correct = true, isReview = false)
        }

        assertEquals(0, repository.newWords().size)
        assertEquals(20, database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")?.completedCount)
        assertEquals(20, repository.newWords(continueAfterGoal = true).size)
        assertTrue(repository.newWords(continueAfterGoal = true).all { it.source == WordSource.NCE1 })
    }

    @Test fun hintedRecognitionPreservesMistakeHistoryAndReturnsTomorrowForBothWordPacks() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val tomorrow = LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        listOf(WordSource.NCE1, WordSource.CET4).forEach { source ->
            val word = database.wordDao().getNewBySource(source, 1).single().copy(wrongCount = 2)
            database.wordDao().upsert(word)
            repository.answerWord(word, correct = true, isReview = false, independentRecall = false)
            // A repeat tap or switching practice mode must not upgrade this answer.
            repository.answerWord(word, correct = true, isReview = false, independentRecall = true)
            val saved = database.wordDao().getById(word.id)!!
            assertEquals("LEARNING", saved.status)
            assertEquals(0, saved.correctStreak)
            assertEquals(2, saved.wrongCount)
            assertEquals(tomorrow, saved.nextReviewAt)
            assertTrue(repository.dueWords(now = tomorrow + 1000).any { it.id == word.id })
        }
        assertEquals(1, database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")!!.completedCount)
        assertEquals(1, database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!.completedCount)
    }

    @Test
    fun existingDailyPlanMigratesSentenceTaskToThirtyCet4Words() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val date = CoachRepository.today()
        val oldTask = requireNotNull(database.planDao().getTask(date, "SENTENCE_STUDY"))
        database.planDao().upsertTask(
            oldTask.copy(
                title = "精读句子",
                description = "5 个基础句子",
                targetCount = 5,
                completedCount = 3,
                completed = false
            )
        )

        repository.ensureTodayPlan()

        val migrated = requireNotNull(database.planDao().getTask(date, "SENTENCE_STUDY"))
        assertEquals("四级词汇", migrated.title)
        assertEquals(30, migrated.targetCount)
        assertEquals(3, migrated.completedCount)
        assertFalse(migrated.completed)
    }

    @Test
    fun aBatchFinishesBookOneThenImmediatelyContinuesBookTwoWithoutMasteryGate() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val bookOne = database.wordDao().getNewBySource(WordSource.NCE1, 2_000)
        assertTrue(repository.newWords().all { it.source == WordSource.NCE1 })
        database.openHelper.writableDatabase.execSQL("UPDATE words SET status = 'LEARNING' WHERE source = 'NCE1'")
        bookOne.takeLast(2).forEach { database.wordDao().upsert(it) }

        val batch = repository.newWords()
        assertEquals(20, batch.size)
        assertEquals(bookOne.takeLast(2).map { it.id }, batch.take(2).map { it.id })
        assertTrue(batch.drop(2).all { it.source == WordSource.NCE2 })
        assertEquals("private", batch[2].word)
        batch.take(2).forEach { repository.answerWord(it, true, false) }
        assertEquals("private", repository.newWords().first().word)
        assertEquals(18, repository.newWords().size)
        assertTrue(database.wordDao().observeAll().first().filter { it.source == WordSource.NCE1 }.all { it.status != "MASTERED" })
    }

    @Test
    fun secondBookAlsoStopsAtTwentyThenAllowsExtraStudyAndHandlesExhaustion() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        database.openHelper.writableDatabase.execSQL("UPDATE words SET status = 'LEARNING' WHERE source = 'NCE1'")
        val batch = repository.newWords()
        assertTrue(batch.all { it.source == WordSource.NCE2 })
        batch.forEach { repository.answerWord(it, true, false) }
        assertTrue(repository.newWords().isEmpty())
        val extra = repository.newWords(continueAfterGoal = true)
        assertEquals(20, extra.size)
        assertTrue(extra.none { next -> batch.any { it.id == next.id } })
        assertEquals(20, database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")?.targetCount)
        database.openHelper.writableDatabase.execSQL("UPDATE words SET status = 'LEARNING' WHERE source = 'NCE2'")
        assertTrue(repository.newWords(continueAfterGoal = true).isEmpty())
    }

    @Test
    fun bothNceBooksShareHalfTheReviewQuotaAndAreOrderedByDueDate() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val start = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val dao = database.wordDao()
        for (source in listOf(WordSource.NCE1, WordSource.NCE2, WordSource.CET4)) {
            dao.getNewBySource(source, 10).forEach { word ->
                dao.upsert(word.copy(status = "LEARNING", updatedAt = start - 2 * DAY_MS,
                    nextReviewAt = if (source == WordSource.NCE2) start - DAY_MS else start))
            }
        }
        val due = repository.dueWords(start + 1_000)
        assertEquals(20, due.size)
        assertEquals(10, due.count { WordSource.isNewConcept(it.source) })
        assertEquals(10, due.count { it.source == WordSource.CET4 })
        assertEquals(WordSource.NCE2, due.first().source)
        due.forEachIndexed { index, word -> assertEquals(if (index % 2 == 0) WordSource.NCE2 else WordSource.CET4, word.source) }
    }

    @Test
    fun bookTwoJoinsTomorrowReviewButIsNotRepeatedTodayOrWhileStillNew() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val word = database.wordDao().getNewBySource(WordSource.NCE2, 1).single()
        repository.answerWord(word, true, false)
        assertTrue(repository.dueWords().none { it.source == WordSource.NCE2 })
        val next = database.wordDao().getById(word.id)!!
        val tomorrowNoon = LocalDate.now().plusDays(1).atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val future = maxOf(tomorrowNoon, next.nextReviewAt)
        assertEquals(listOf(word.id), repository.dueWords(future).filter { it.source == WordSource.NCE2 }.map { it.id })
    }

    @Test
    fun addingBookTwoIsIdempotentAndPreservesProgressInBothBooksAndCet4() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val dao = database.wordDao()
        val originals = listOf(WordSource.NCE1, WordSource.NCE2, WordSource.CET4).map { source ->
            dao.getNewBySource(source, 1).single().copy(status = "REVIEWING", correctStreak = 2,
                wrongCount = 3, nextReviewAt = 123456L, lastWrongAt = 300L, createdAt = 100L, updatedAt = 500L)
        }
        originals.forEach { dao.upsert(it.copy(example = "Old example.")) }
        val count = dao.count()
        val task = database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")!!
        database.planDao().upsertTask(task.copy(completedCount = 17, description = "旧版第一册计划"))
        repeat(2) { repository.initializeIfNeeded() }
        assertEquals(count, dao.count())
        assertEquals(Nce2WordPack.UNIQUE_WORD_COUNT, dao.observeAll().first().count { it.source == WordSource.NCE2 })
        originals.forEach { assertEquals(it, dao.getById(it.id)) }
        val updatedTask = database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")!!
        assertEquals(17, updatedTask.completedCount)
        assertTrue(updatedTask.description.contains("第二册"))
    }

    @Test
    fun appUpgradeAddsNcePackWithoutResettingExistingWordProgress() = runTest {
        val now = System.currentTimeMillis()
        database.wordDao().insert(
            WordItemEntity(
                word = "book",
                phonetic = "/bʊk/",
                meaning = "n. 书",
                example = "This book is useful.",
                exampleTranslation = "这本书很有用。",
                level = "A0-A1",
                status = "REVIEWING",
                correctStreak = 2,
                nextReviewAt = now,
                createdAt = now - 10_000,
                updatedAt = now - 5_000
            )
        )

        repository.initializeIfNeeded()

        assertTrue(database.wordDao().count() >= Nce1WordPack.UNIQUE_WORD_COUNT)
        val book = database.wordDao().find("book")
        assertEquals("REVIEWING", book?.status)
        assertEquals(2, book?.correctStreak)
        assertEquals(WordSource.NCE1, book?.source)
        assertFalse(book?.example?.contains("remember the word", ignoreCase = true) == true)
    }

    @Test
    fun appUpgradeReplacesGenericNceExampleWithoutResettingReviewProgress() = runTest {
        val now = System.currentTimeMillis()
        database.wordDao().insert(
            WordItemEntity(
                word = "Sweden",
                phonetic = "/ˈswiːd(ə)n/",
                meaning = "n. 瑞典",
                example = "Please remember the word \"Sweden\".",
                exampleTranslation = "请记住单词“Sweden”。",
                level = "NCE1 Lesson 5&6, NCE1 Lesson 051&52",
                status = "REVIEWING",
                correctStreak = 2,
                wrongCount = 1,
                nextReviewAt = now + DAY_MS,
                lastWrongAt = now - DAY_MS,
                createdAt = now - 10_000,
                updatedAt = now - 5_000
            )
        )

        repository.initializeIfNeeded()

        val sweden = database.wordDao().find("Sweden")
        assertEquals("REVIEWING", sweden?.status)
        assertEquals(2, sweden?.correctStreak)
        assertEquals(1, sweden?.wrongCount)
        assertEquals(now + DAY_MS, sweden?.nextReviewAt)
        assertEquals(now - DAY_MS, sweden?.lastWrongAt)
        assertEquals(now - 5_000, sweden?.updatedAt)
        assertTrue(sweden?.example?.contains("Sweden") == true)
        assertTrue(sweden?.exampleTranslation?.contains("瑞典") == true)
        assertFalse(sweden?.example?.contains("remember the word", ignoreCase = true) == true)
    }

    @Test
    fun dailyReviewIncludesYesterdayAndEarlierButNotWordsStudiedToday() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )
        val zone = ZoneId.systemDefault()
        val dayStart = LocalDate.now().atStartOfDay(zone).toInstant().toEpochMilli()
        val now = dayStart + 12 * 60 * 60 * 1000L
        fun reviewedWord(word: String, updatedAt: Long, nextReviewAt: Long) = WordItemEntity(
            word = word,
            phonetic = "/test/",
            meaning = "测试词",
            example = "Please remember the word \"$word\".",
            exampleTranslation = "请记住这个测试词。",
            level = "TEST",
            status = "LEARNING",
            correctStreak = 1,
            nextReviewAt = nextReviewAt,
            createdAt = updatedAt,
            updatedAt = updatedAt
        )
        database.wordDao().insertAll(
            listOf(
                reviewedWord("twodaysago", dayStart - 2 * DAY_MS, dayStart - DAY_MS),
                reviewedWord("yesterdayword", dayStart - DAY_MS, dayStart),
                reviewedWord("todayword", dayStart + 1_000, 0)
            )
        )

        val due = repository.dueWords(now)

        assertEquals(listOf("twodaysago", "yesterdayword"), due.map { it.word })
    }

    @Test
    fun dailyReviewBalancesCet4AndNceWordsWhenBothAreDue() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val dayStart = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val now = dayStart + 12 * 60 * 60 * 1000L
        val candidates = listOf(
            requireNotNull(database.wordDao().findBySource("book", WordSource.NCE1)),
            requireNotNull(database.wordDao().findBySource("work", WordSource.NCE1)),
            requireNotNull(database.wordDao().findBySource("ability", WordSource.CET4)),
            requireNotNull(database.wordDao().findBySource("about", WordSource.CET4))
        )
        candidates.forEach { word ->
            database.wordDao().upsert(
                word.copy(status = "LEARNING", nextReviewAt = dayStart, updatedAt = dayStart - DAY_MS)
            )
        }

        val due = repository.dueWords(now)

        assertEquals(4, due.size)
        assertEquals(2, due.count { it.source == WordSource.NCE1 })
        assertEquals(2, due.count { it.source == WordSource.CET4 })
        assertEquals(
            listOf(WordSource.NCE1, WordSource.CET4, WordSource.NCE1, WordSource.CET4),
            due.map { it.source }
        )
    }

    @Test
    fun studyingCet4WordUsesTheSameRecordForReviewAndDoesNotConsumeNceGoal() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val word = repository.cet4Words().first()
        repeat(2) { repository.answerWord(word, true, false) }
        val learned = database.wordDao().getById(word.id)!!
        assertEquals("LEARNING", learned.status)
        assertEquals(1, learned.correctStreak)
        assertEquals(1, database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!.completedCount)
        assertEquals(0, database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")!!.completedCount)
        assertTrue(repository.cet4Words().none { it.id == word.id })
        assertTrue(repository.dueWords().none { it.id == word.id })
        val tomorrow = maxOf(learned.nextReviewAt, LocalDate.now().plusDays(1).atTime(12, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        assertEquals(word.id, repository.dueWords(tomorrow).single().id)
    }

    @Test
    fun sentenceAnalysisDoesNotConsumeThePaulWordGoal() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        repository.recordSentenceStudy("I like reading.")
        repository.recordSentenceStudy("  i LIKE reading.  ")

        assertEquals(
            0,
            database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")?.completedCount
        )
        assertEquals(1, repository.todayTotals().first().first { it.type == "SENTENCE" }.amount)
    }

    @Test
    fun retiredWritingHistoryRemainsDeduplicatedButDoesNotCreateDailyTask() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        val writing = "I like reading. It helps me relax. I read every night."
        repository.recordWriting("写三句话", writing)
        repository.recordWriting("写三句话", writing)

        assertEquals(null, database.planDao().getTask(CoachRepository.today(), "WRITING"))
        assertEquals(3, repository.todayTotals().first().first { it.type == "WRITING" }.amount)
    }

    @Test
    fun cet4WordSessionStopsAtThirtyAndCanContinueWithAnotherBatch() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val words = repository.cet4Words()
        assertEquals(30, words.size)
        assertTrue(words.all { it.source == WordSource.CET4 })
        words.forEach { repository.answerWord(it, true, false) }
        assertTrue(repository.cet4Words().isEmpty())
        val task = database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!
        assertEquals(30, task.completedCount)
        assertEquals(30, task.targetCount)
        val next = repository.cet4Words(continueAfterGoal = true)
        assertEquals(30, next.size)
        assertTrue(next.none { other -> words.any { it.id == other.id } })
        next.forEach { repository.answerWord(it, false, false) }
        assertEquals(60, database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!.completedCount)
        assertEquals(30, database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!.targetCount)
        assertTrue(repository.cet4Words(true).none { other -> (words + next).any { it.id == other.id } })
        database.openHelper.writableDatabase.execSQL("UPDATE words SET status = 'LEARNING' WHERE source = 'CET4'")
        assertTrue(repository.cet4Words(true).isEmpty())
    }

    @Test fun cet4ReplacementPreservesLegacyIdsSchedulesAndSentenceHistory() = runTest {
        val legacy = WordItemEntity(word = "english", phonetic = "/old/", meaning = "旧释义",
            example = "Old example.", exampleTranslation = "旧例句", level = "Paul1000",
            source = WordSource.PAUL1000, status = "REVIEWING", correctStreak = 2, wrongCount = 4,
            nextReviewAt = 987654L, lastWrongAt = 888L, createdAt = 111L, updatedAt = 222L)
        val legacyId = database.wordDao().insert(legacy)
        val archived = legacy.copy(word = "legacy-only-token")
        val archivedId = database.wordDao().insert(archived)
        val card = com.personalieltscoach.data.seed.Paul1000SentencePack.cards(0).first()
            .copy(status = "REVIEWING", correctStreak = 2, nextReviewAt = 333L)
        database.sentenceCardDao().upsert(card)
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val task = database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!
        database.planDao().upsertTask(task.copy(title = "Paul1000单词", completedCount = 17))
        repeat(2) { repository.initializeIfNeeded() }
        val migrated = database.wordDao().findBySource("English", WordSource.CET4)!!
        assertEquals(legacyId, migrated.id)
        assertEquals("English", migrated.word)
        assertEquals(legacy.status, migrated.status)
        assertEquals(legacy.correctStreak, migrated.correctStreak)
        assertEquals(legacy.wrongCount, migrated.wrongCount)
        assertEquals(legacy.nextReviewAt, migrated.nextReviewAt)
        assertEquals(legacy.lastWrongAt, migrated.lastWrongAt)
        assertEquals(legacy.createdAt, migrated.createdAt)
        assertEquals(legacy.updatedAt, migrated.updatedAt)
        assertEquals(archived.copy(id = archivedId), database.wordDao().getById(archivedId))
        assertEquals(1, database.sentenceCardDao().count())
        assertEquals(card, database.sentenceCardDao().getDue(Long.MAX_VALUE, Long.MAX_VALUE, 10).single())
        assertEquals(4458, database.wordDao().getBySource(WordSource.CET4).size)
        val updatedTask = database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")!!
        assertEquals("四级词汇", updatedTask.title)
        assertEquals(17, updatedTask.completedCount)
        assertEquals(13, repository.cet4Words().size)
        assertTrue(repository.dueWords().none { it.source == WordSource.PAUL1000 })
    }

    @Test fun freshInstallHasCet4ButNoPaulAssignmentsOrSentenceCards() = runTest {
        repository.initializeIfNeeded()
        assertEquals(4458, database.wordDao().getBySource(WordSource.CET4).size)
        assertTrue(database.wordDao().getBySource(WordSource.PAUL1000).isEmpty())
        assertEquals(0, database.sentenceCardDao().count())
    }

    @Test fun oldFiveTaskPlanRetiresWritingWithoutResettingCompletedLearning() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val date = CoachRepository.today()
        database.planDao().getTasks(date).forEach {
            database.planDao().upsertTask(it.copy(completedCount = it.targetCount, completed = true))
        }
        database.planDao().insertTasks(listOf(com.personalieltscoach.data.local.entity.StudyTaskEntity(
            date = date, type = "WRITING", title = "写作练习", description = "5 个句子", targetCount = 5)))
        val old = requireNotNull(database.planDao().getPlan(date))
        database.planDao().upsertPlan(old.copy(totalCount = 5, completedCount = 4))
        repository.recordWriting("历史练习", "I work here.")
        val records = repository.todayTotals().first()

        repeat(2) { repository.ensureTodayPlan() }
        repository.ensureTodayPlan(force = true)

        val tasks = database.planDao().getTasks(date)
        assertEquals(setOf("VOCAB_NEW", "VOCAB_REVIEW", "SENTENCE_STUDY", "READING"), tasks.map { it.type }.toSet())
        assertEquals(30, tasks.single { it.type == "SENTENCE_STUDY" }.completedCount)
        assertEquals(20, tasks.single { it.type == "VOCAB_NEW" }.completedCount)
        assertTrue(tasks.all { it.completed })
        assertEquals(4, database.planDao().getPlan(date)?.completedCount)
        assertEquals(4, database.planDao().getPlan(date)?.totalCount)
        assertEquals(records, repository.todayTotals().first())
    }

    companion object {
        private const val DAY_MS = 24L * 60L * 60L * 1000L
    }
}
