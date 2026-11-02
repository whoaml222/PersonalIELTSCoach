package com.personalieltscoach.data.repository

import androidx.room.withTransaction
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.data.seed.Nce1WordPack
import com.personalieltscoach.data.seed.Nce2WordPack
import com.personalieltscoach.data.seed.Cet4WordPack
import com.personalieltscoach.data.seed.SeedData
import com.personalieltscoach.domain.model.PlacementResult
import com.personalieltscoach.domain.service.ReviewScheduler
import com.personalieltscoach.domain.service.SentencePackStats
import com.personalieltscoach.domain.service.StreakCalculator
import com.personalieltscoach.domain.service.TextSegmenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

class CoachRepository(
    private val database: CoachDatabase,
    private val settingsRepository: SettingsProvider,
    private val json: Json
) {
    val profile: Flow<UserProfileEntity?> = database.userProfileDao().observe()
    val allWords: Flow<List<WordItemEntity>> = database.wordDao().observeAll()
    val wrongWords: Flow<List<WordItemEntity>> = database.wordDao().observeWrong()
    val readings: Flow<List<ReadingTextEntity>> = database.contentDao().observeReadings()
    val writingRecords: Flow<List<WritingRecordEntity>> = database.writingDao().observeAll()
    val masteredCount: Flow<Int> = database.wordDao().observeStatusCount("MASTERED")
    val learningCount: Flow<Int> = database.wordDao().observeStatusCount("LEARNING")
    val reviewingCount: Flow<Int> = database.wordDao().observeStatusCount("REVIEWING")
    val wrongCount: Flow<Int> = database.wordDao().observeWrongCount()
    val writingCount: Flow<Int> = database.writingDao().observeCount()
    val cet4Stats: Flow<SentencePackStats> = allWords.map { words ->
        val cet4 = words.filter { it.source == WordSource.CET4 }
        SentencePackStats(total = cet4.size, started = cet4.count { it.status != "NEW" },
            mastered = cet4.count { it.status == "MASTERED" })
    }

    fun plan(date: String): Flow<DailyPlanEntity?> = database.planDao().observePlan(date)
    fun tasks(date: String): Flow<List<StudyTaskEntity>> = database.planDao().observeTasks(date)
    fun totals(date: String) = database.studyDao().observeTotals(date)
    fun minutes(date: String) = database.studyDao().observeMinutes(date)
    fun todayPlan(): Flow<DailyPlanEntity?> = plan(today())
    fun todayTasks(): Flow<List<StudyTaskEntity>> = tasks(today())
    fun todayTotals() = totals(today())
    fun todayMinutes() = minutes(today())

    suspend fun initializeIfNeeded() {
        database.withTransaction {
            val now = System.currentTimeMillis()
            val nceWords = Nce1WordPack.words(now, emptyList())
            val nce2Words = Nce2WordPack.words(now)
            val cet4Words = Cet4WordPack.words(now)
            val cet4ByKey = cet4Words.associateBy { it.word.lowercase(Locale.ROOT) }
            database.wordDao().getBySource(WordSource.PAUL1000).forEach { legacy ->
                cet4ByKey[legacy.word.lowercase(Locale.ROOT)]?.let { current ->
                    database.wordDao().movePaulProgressToCet4(legacy.id, current.word)
                }
            }
            val existingCet4 = database.wordDao().getBySource(WordSource.CET4)
                .associateBy { it.word.lowercase(Locale.ROOT) }
            // INSERT IGNORE turns every app upgrade into an incremental content
            // merge. Existing progress is retained while newly bundled packs are
            // added to accounts that have already studied the original 100 words.
            database.wordDao().insertAll(
                SeedData.words(now) + nceWords + nce2Words + cet4Words
            )
            // If a learner already knew the same word in the original core pack,
            // do not show it again as a brand-new NCE word after upgrading.
            database.wordDao().inheritLegacyProgressForNce()
            SeedData.words(now).forEach { word ->
                database.wordDao().updateCoreExample(word.word, word.example, word.exampleTranslation)
            }
            // Content can improve independently from review progress. Update only
            // the bundled NCE fields, leaving ids, status, streaks, wrong answers,
            // review dates and timestamps untouched for existing learners.
            (nceWords + nce2Words).forEach { word ->
                database.wordDao().updateNceLearningContent(
                    word = word.word,
                    phonetic = word.phonetic,
                    meaning = word.meaning,
                    example = word.example,
                    exampleTranslation = word.exampleTranslation,
                    level = word.level,
                    source = word.source
                )
            }
            cet4Words.forEach { word ->
                val previous = existingCet4[word.word.lowercase(Locale.ROOT)] ?: return@forEach
                if (previous.phonetic == word.phonetic && previous.meaning == word.meaning &&
                    previous.example == word.example && previous.exampleTranslation == word.exampleTranslation &&
                    previous.level == word.level) return@forEach
                database.wordDao().updateNceLearningContent(
                    word = word.word,
                    phonetic = word.phonetic,
                    meaning = word.meaning,
                    example = word.example,
                    exampleTranslation = word.exampleTranslation,
                    level = word.level,
                    source = WordSource.CET4
                )
            }
            if (database.contentDao().questionCount() == 0) {
                database.contentDao().insertQuestions(SeedData.questions(json))
            }
            if (database.contentDao().readingCount() == 0) {
                database.contentDao().insertReadings(SeedData.readings(now))
            }
            // Retired sentence-card progress stays untouched; CET4 uses word records only.
        }
        if (database.userProfileDao().get() != null) ensureTodayPlan()
    }

    suspend fun placementQuestions(): List<PlacementQuestionEntity> =
        database.contentDao().questions()

    suspend fun hasProfile(): Boolean = database.userProfileDao().get() != null

    suspend fun savePlacement(result: PlacementResult) {
        val now = System.currentTimeMillis()
        database.userProfileDao().upsert(
            UserProfileEntity(
                currentLevel = result.level,
                estimatedVocabulary = result.estimatedVocabulary,
                weakSkills = result.weakSkills,
                createdAt = now,
                lastStudyDate = now
            )
        )
        ensureTodayPlan(force = true)
    }

    suspend fun ensureTodayPlan(force: Boolean = false) {
        val date = today()
        val profile = database.userProfileDao().get() ?: return
        val existingPlan = database.planDao().getPlan(date)
        if (!force && existingPlan != null) {
            val newWordTask = database.planDao().getTask(date, "VOCAB_NEW")
            val cet4WordTask = database.planDao().getTask(date, "SENTENCE_STUDY")
            val updatedNewWordTask = newWordTask?.copy(
                description = "$NEW_WORD_DAILY_GOAL 个新概念词汇，第一册后接第二册，可继续加学",
                targetCount = NEW_WORD_DAILY_GOAL,
                completed = newWordTask.completedCount >= NEW_WORD_DAILY_GOAL
            )
            val updatedCet4WordTask = cet4WordTask?.copy(
                title = "四级词汇",
                description = "$CET4_DAILY_GOAL 个四级单词，完成后可继续",
                targetCount = CET4_DAILY_GOAL,
                completed = cet4WordTask.completedCount >= CET4_DAILY_GOAL
            )
            val reviewTask = database.planDao().getTask(date, "VOCAB_REVIEW")
            val updatedReviewTask = reviewTask?.copy(description = "${reviewTask.targetCount} 个到期词：四级与新概念各半")
            val retiredWritingTask = database.planDao().getTask(date, "WRITING")
            if (updatedNewWordTask != newWordTask || updatedCet4WordTask != cet4WordTask ||
                updatedReviewTask != reviewTask || retiredWritingTask != null || existingPlan.totalCount != 4) {
                database.withTransaction {
                    database.planDao().removeRetiredWritingTask(date)
                    updatedNewWordTask?.let { database.planDao().upsertTask(it) }
                    updatedCet4WordTask?.let { database.planDao().upsertTask(it) }
                    updatedReviewTask?.let { database.planDao().upsertTask(it) }
                    val tasks = database.planDao().getTasks(date)
                    database.planDao().upsertPlan(existingPlan.copy(completedCount = tasks.count { it.completed }, totalCount = tasks.size))
                }
            }
            return
        }
        val settings = settingsRepository.current()
        val a1Plus = profile.currentLevel != "A0-A1"
        val reviewTarget = if (a1Plus) maxOf(settings.dailyReviewWords, 30) else settings.dailyReviewWords
        val now = System.currentTimeMillis()
        val tasks = listOf(
            StudyTaskEntity(date = date, type = "VOCAB_REVIEW", title = "复习旧单词", description = "$reviewTarget 个到期词：四级与新概念各半", targetCount = reviewTarget),
            StudyTaskEntity(date = date, type = "VOCAB_NEW", title = "学习新单词", description = "$NEW_WORD_DAILY_GOAL 个新概念词汇，第一册后接第二册，可继续加学", targetCount = NEW_WORD_DAILY_GOAL),
            StudyTaskEntity(date = date, type = "SENTENCE_STUDY", title = "四级词汇", description = "$CET4_DAILY_GOAL 个四级单词，完成后可继续", targetCount = CET4_DAILY_GOAL),
            StudyTaskEntity(date = date, type = "READING", title = "阅读短文", description = if (a1Plus) "阅读 100-200 词" else "阅读 50-100 词", targetCount = 1)
        )
        database.withTransaction {
            database.planDao().removeRetiredWritingTask(date)
            database.planDao().insertTasks(tasks)
            val savedTasks = database.planDao().getTasks(date)
            database.planDao().upsertPlan(
                DailyPlanEntity(
                    id = existingPlan?.id ?: 0,
                    date = date,
                    level = profile.currentLevel,
                    completedCount = savedTasks.count { it.completed },
                    totalCount = savedTasks.size,
                    createdAt = existingPlan?.createdAt ?: now
                )
            )
        }
    }

    suspend fun newWords(continueAfterGoal: Boolean = false): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_NEW") ?: return emptyList()
        val remainingGoal = (task.targetCount - task.completedCount).coerceAtLeast(0)
        val limit = if (remainingGoal > 0) remainingGoal else if (continueAfterGoal) EXTRA_WORD_BATCH else 0
        if (limit == 0) return emptyList()
        return database.wordDao().getNewConceptWords(limit)
    }

    suspend fun dueWords(now: Long = System.currentTimeMillis()): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_REVIEW") ?: return emptyList()
        val limit = (task.targetCount - task.completedCount).coerceAtLeast(0)
        if (limit == 0) return emptyList()
        val start = dayStart(now)
        val nceTarget = (limit + 1) / 2
        val cet4Target = limit / 2
        val nce = database.wordDao().getDueNewConcept(now, start, nceTarget)
        val cet4 = database.wordDao().getDueBySource(WordSource.CET4, now, start, cet4Target)

        val selected = interleave(nce, cet4).toMutableList()
        var remaining = limit - selected.size
        if (remaining > 0) {
            val selectedIds = selected.mapTo(mutableSetOf()) { it.id }
            val extraNce = database.wordDao()
                .getDueNewConcept(now, start, limit)
                .filterNot { it.id in selectedIds }
            val extraCet4 = database.wordDao()
                .getDueBySource(WordSource.CET4, now, start, limit)
                .filterNot { it.id in selectedIds }
            (extraNce + extraCet4).take(remaining).forEach {
                selected += it
                selectedIds += it.id
            }
            remaining = limit - selected.size
            if (remaining > 0) {
                database.wordDao()
                    .getDueBySource(WordSource.CORE, now, start, remaining)
                    .filterNot { it.id in selectedIds }
                    .take(remaining)
                    .forEach(selected::add)
            }
        }
        return selected
    }

    suspend fun cet4Words(continueAfterGoal: Boolean = false): List<WordItemEntity> {
        ensureTodayPlan()
        // Keep the stable task key to retain today's pre-upgrade completion count.
        val task = database.planDao().getTask(today(), "SENTENCE_STUDY") ?: return emptyList()
        val remainingGoal = (task.targetCount - task.completedCount).coerceAtLeast(0)
        val limit = if (remainingGoal > 0) remainingGoal else if (continueAfterGoal) CET4_EXTRA_BATCH else 0
        return if (limit == 0) emptyList() else database.wordDao().getNewBySource(WordSource.CET4, limit)
    }

    suspend fun answerWord(word: WordItemEntity, correct: Boolean, isReview: Boolean) {
        ensureTodayPlan()
        val now = System.currentTimeMillis()
        database.withTransaction {
            val fresh = database.wordDao().getById(word.id) ?: return@withTransaction
            if (fresh.status != "NEW" && fresh.updatedAt >= dayStart(now)) return@withTransaction
            val update = ReviewScheduler.next(correct, fresh.correctStreak, fresh.wrongCount, now)
            database.wordDao().upsert(
                fresh.copy(
                    status = update.status,
                    correctStreak = update.correctStreak,
                    wrongCount = update.wrongCount,
                    nextReviewAt = update.nextReviewAt,
                    lastWrongAt = if (correct) fresh.lastWrongAt else now,
                    updatedAt = now
                )
            )
            val date = today()
            val activity = if (isReview) "REVIEW_WORD" else "NEW_WORD"
            val referenceKey = "word:${word.id}"
            if (database.studyDao().countByReference(date, activity, referenceKey) == 0) {
                markStudyDay()
                database.studyDao().insert(
                    StudyActivityEntity(
                        date = date,
                        type = activity,
                        referenceKey = referenceKey,
                        amount = 1,
                        durationMinutes = 1,
                        createdAt = now
                    )
                )
                incrementTask(when {
                    isReview -> "VOCAB_REVIEW"
                    fresh.source == WordSource.CET4 -> "SENTENCE_STUDY"
                    else -> "VOCAB_NEW"
                }, 1)
            }
            if (!correct) {
                database.studyDao().insert(
                    StudyActivityEntity(
                        date = date,
                        type = "WRONG_WORD",
                        amount = 1,
                        durationMinutes = 1,
                        createdAt = now
                    )
                )
            }
        }
    }

    suspend fun addUnknownWord(rawWord: String) {
        val word = rawWord.lowercase().trim().replace(Regex("[^a-z'-]"), "")
        if (word.isBlank() || database.wordDao().find(word) != null) return
        val now = System.currentTimeMillis()
        database.wordDao().insert(
            WordItemEntity(
                word = word,
                phonetic = "",
                meaning = "待补充释义",
                example = "",
                exampleTranslation = "",
                level = "A0-A1",
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun findWord(word: String): WordItemEntity? =
        database.wordDao().find(word.lowercase().trim().replace(Regex("[^a-z'-]"), ""))

    suspend fun saveSentence(sentence: String, translation: String) {
        database.contentDao().saveSentence(
            SavedSentenceEntity(sentence = sentence, translation = translation, createdAt = System.currentTimeMillis())
        )
    }

    suspend fun recordSentenceStudy(sentence: String) {
        recordUniqueActivity("SENTENCE", 1, stableKey(sentence))
    }

    suspend fun recordReading(text: String) {
        val wordCount = Regex("[A-Za-z]+(?:'[A-Za-z]+)?").findAll(text).count()
        if (wordCount == 0) return
        if (recordUniqueActivity("READING_WORD", wordCount, stableKey(text))) {
            incrementTask("READING", 1)
        }
    }

    suspend fun recordWriting(prompt: String, text: String) {
        val sentenceCount = TextSegmenter.sentences(text).size
            .coerceAtLeast(if (text.isBlank()) 0 else 1)
        if (
            sentenceCount > 0 &&
            recordUniqueActivity("WRITING", sentenceCount, stableKey("$prompt|$text"))
        ) {
            incrementTask("WRITING", sentenceCount)
        }
    }

    private suspend fun recordUniqueActivity(type: String, amount: Int, referenceKey: String): Boolean =
        database.withTransaction {
            val date = today()
            if (database.studyDao().countByReference(date, type, referenceKey) > 0) {
                false
            } else {
                markStudyDay()
                database.studyDao().insert(
                    StudyActivityEntity(
                        date = date,
                        type = type,
                        referenceKey = referenceKey,
                        amount = amount,
                        durationMinutes = 1,
                        createdAt = System.currentTimeMillis()
                    )
                )
                true
            }
        }

    private suspend fun markStudyDay() {
        val profile = database.userProfileDao().get() ?: return
        val now = System.currentTimeMillis()
        val lastDate = Instant.ofEpochMilli(profile.lastStudyDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val studyDate = LocalDate.now()
        if (lastDate != studyDate) {
            database.userProfileDao().upsert(
                profile.copy(
                    streakDays = StreakCalculator.next(profile.streakDays, lastDate, studyDate),
                    lastStudyDate = now
                )
            )
        }
    }

    private suspend fun incrementTask(type: String, amount: Int) {
        val date = today()
        val task = database.planDao().getTask(date, type) ?: return
        val count = if (type == "SENTENCE_STUDY") task.completedCount + amount
            else (task.completedCount + amount).coerceAtMost(task.targetCount)
        database.planDao().upsertTask(task.copy(completedCount = count, completed = count >= task.targetCount))
        val tasks = database.planDao().getTasks(date)
        val plan = database.planDao().getPlan(date) ?: return
        database.planDao().upsertPlan(plan.copy(completedCount = tasks.count { it.completed }))
    }

    suspend fun clearLearningData() {
        database.withTransaction {
            database.userProfileDao().clear()
            database.wordDao().clear()
            database.planDao().clearPlans()
            database.planDao().clearTasks()
            database.writingDao().clear()
            database.studyDao().clear()
            database.contentDao().clearSavedSentences()
            database.sentenceCardDao().clear()
            database.aiDao().clearSentenceCache()
            database.aiDao().clearResponseCache()
            database.aiDao().clearUsage()
            database.readingDao().clearProgress()
            database.readingDao().clearVocabulary()
        }
        initializeIfNeeded()
    }

    companion object {
        const val NEW_WORD_DAILY_GOAL = 20
        const val CET4_DAILY_GOAL = 30
        private const val EXTRA_WORD_BATCH = 20
        private const val CET4_EXTRA_BATCH = 30

        fun today(): String = LocalDate.now().toString()

        private fun dayStart(time: Long): Long = Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        private fun stableKey(value: String): String = MessageDigest.getInstance("SHA-256")
            .digest(value.trim().lowercase().toByteArray())
            .joinToString("") { "%02x".format(it) }

        private fun interleave(
            first: List<WordItemEntity>,
            second: List<WordItemEntity>
        ): List<WordItemEntity> = buildList {
            repeat(maxOf(first.size, second.size)) { index ->
                first.getOrNull(index)?.let(::add)
                second.getOrNull(index)?.let(::add)
            }
        }
    }
}
