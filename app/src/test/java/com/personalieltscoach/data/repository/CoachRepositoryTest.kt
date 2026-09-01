package com.personalieltscoach.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.WordSource
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.seed.Nce1WordPack
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
    fun dailyReviewBalancesPaulAndNceWordsWhenBothAreDue() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val dayStart = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val now = dayStart + 12 * 60 * 60 * 1000L
        val candidates = listOf(
            requireNotNull(database.wordDao().findBySource("book", WordSource.NCE1)),
            requireNotNull(database.wordDao().findBySource("work", WordSource.NCE1)),
            requireNotNull(database.wordDao().findBySource("ability", WordSource.PAUL1000)),
            requireNotNull(database.wordDao().findBySource("about", WordSource.PAUL1000))
        )
        candidates.forEach { word ->
            database.wordDao().upsert(
                word.copy(status = "LEARNING", nextReviewAt = dayStart, updatedAt = dayStart - DAY_MS)
            )
        }

        val due = repository.dueWords(now)

        assertEquals(4, due.size)
        assertEquals(2, due.count { it.source == WordSource.NCE1 })
        assertEquals(2, due.count { it.source == WordSource.PAUL1000 })
        assertEquals(
            listOf(WordSource.NCE1, WordSource.PAUL1000, WordSource.NCE1, WordSource.PAUL1000),
            due.map { it.source }
        )
    }

    @Test
    fun studyingPaulSentenceAddsItsFocusWordToVocabularyReview() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(PlacementResult("A0-A1", 300, "词汇", "基础路线"))
        val card = repository.sentenceSession(limit = 1).single()
        val focusWord = requireNotNull(com.personalieltscoach.data.seed.Paul1000SentencePack.focusWord(card.id))

        repository.answerSentence(
            card,
            com.personalieltscoach.domain.service.SentenceRating.REMEMBERED
        )

        val word = requireNotNull(database.wordDao().findBySource(focusWord, WordSource.PAUL1000))
        assertTrue(word.status != "NEW")
        assertTrue(word.nextReviewAt > 0)
    }

    @Test
    fun sameSentenceOnlyCountsOncePerDay() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        repository.recordSentenceStudy("I like reading.")
        repository.recordSentenceStudy("  i LIKE reading.  ")

        assertEquals(
            1,
            database.planDao().getTask(CoachRepository.today(), "SENTENCE_STUDY")?.completedCount
        )
        assertEquals(1, repository.todayTotals().first().first { it.type == "SENTENCE" }.amount)
    }

    @Test
    fun writingProgressCountsSentencesAndDeduplicatesSubmission() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        val writing = "I like reading. It helps me relax. I read every night."
        repository.recordWriting("写三句话", writing)
        repository.recordWriting("写三句话", writing)

        assertEquals(
            3,
            database.planDao().getTask(CoachRepository.today(), "WRITING")?.completedCount
        )
        assertEquals(3, repository.todayTotals().first().first { it.type == "WRITING" }.amount)
    }

    @Test
    fun sentenceSessionsKeepAddingNewCardsWithoutSameDayRepeats() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )
        val seen = mutableSetOf<String>()

        repeat(8) {
            val cards = repository.sentenceSession(limit = 5)
            assertEquals(5, cards.size)
            assertTrue(cards.none { it.id in seen })
            cards.forEach { card ->
                seen += card.id
                repository.answerSentence(
                    card,
                    com.personalieltscoach.domain.service.SentenceRating.REMEMBERED
                )
            }
        }

        assertEquals(40, seen.size)
        assertEquals(40, repository.sentencePackStats.first().started)
        val next = repository.sentenceSession(limit = 5)
        assertEquals(
            listOf("paul-0041", "paul-0042", "paul-0043", "paul-0044", "paul-0045"),
            next.map { it.id }
        )
    }

    companion object {
        private const val DAY_MS = 24L * 60L * 60L * 1000L
    }
}
