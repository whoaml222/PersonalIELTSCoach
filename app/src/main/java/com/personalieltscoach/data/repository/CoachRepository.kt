package com.personalieltscoach.data.repository

import androidx.room.withTransaction
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.data.seed.SeedData
import com.personalieltscoach.domain.model.PlacementResult
import com.personalieltscoach.domain.service.ReviewScheduler
import com.personalieltscoach.domain.service.StreakCalculator
import com.personalieltscoach.domain.service.TextSegmenter
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
            if (database.wordDao().count() == 0) {
                database.wordDao().insertAll(SeedData.words(now))
            }
            if (database.contentDao().questionCount() == 0) {
                database.contentDao().insertQuestions(SeedData.questions(json))
            }
            if (database.contentDao().readingCount() == 0) {
                database.contentDao().insertReadings(SeedData.readings(now))
            }
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
        if (!force && existingPlan != null) return
        val settings = settingsRepository.current()
        val a1Plus = profile.currentLevel != "A0-A1"
        val reviewTarget = if (a1Plus) maxOf(settings.dailyReviewWords, 30) else settings.dailyReviewWords
        val newTarget = if (a1Plus) maxOf(settings.dailyNewWords, 15) else settings.dailyNewWords
        val sentenceTarget = if (a1Plus) maxOf(settings.dailySentences, 8) else settings.dailySentences
        val writingTarget = if (a1Plus) 5 else 3
        val now = System.currentTimeMillis()
        val tasks = listOf(
            StudyTaskEntity(date = date, type = "VOCAB_REVIEW", title = "复习旧单词", description = "$reviewTarget 个到期单词", targetCount = reviewTarget),
            StudyTaskEntity(date = date, type = "VOCAB_NEW", title = "学习新单词", description = "$newTarget 个高频词", targetCount = newTarget),
            StudyTaskEntity(date = date, type = "SENTENCE_STUDY", title = "精读句子", description = "$sentenceTarget 个基础句子", targetCount = sentenceTarget),
            StudyTaskEntity(date = date, type = "READING", title = "阅读短文", description = if (a1Plus) "阅读 100-200 词" else "阅读 50-100 词", targetCount = 1),
            StudyTaskEntity(date = date, type = "WRITING", title = "写作练习", description = "$writingTarget 个简单句", targetCount = writingTarget)
        )
        database.withTransaction {
            database.planDao().upsertPlan(
                DailyPlanEntity(
                    id = existingPlan?.id ?: 0,
                    date = date,
                    level = profile.currentLevel,
                    completedCount = existingPlan?.completedCount ?: 0,
                    totalCount = tasks.size,
                    createdAt = existingPlan?.createdAt ?: now
                )
            )
            database.planDao().insertTasks(tasks)
        }
    }

    suspend fun newWords(): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_NEW") ?: return emptyList()
        val limit = (task.targetCount - task.completedCount).coerceAtLeast(0)
        if (limit == 0) return emptyList()
        return database.wordDao().getNew(limit)
    }

    suspend fun dueWords(): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_REVIEW") ?: return emptyList()
        val limit = (task.targetCount - task.completedCount).coerceAtLeast(0)
        if (limit == 0) return emptyList()
        return database.wordDao().getDue(System.currentTimeMillis(), limit)
    }

    suspend fun answerWord(word: WordItemEntity, correct: Boolean, isReview: Boolean) {
        val now = System.currentTimeMillis()
        val update = ReviewScheduler.next(correct, word.correctStreak, word.wrongCount, now)
        database.wordDao().upsert(
            word.copy(
                status = update.status,
                correctStreak = update.correctStreak,
                wrongCount = update.wrongCount,
                nextReviewAt = update.nextReviewAt,
                lastWrongAt = if (correct) word.lastWrongAt else now,
                updatedAt = now
            )
        )
        val activity = if (isReview) "REVIEW_WORD" else "NEW_WORD"
        val counted = recordUniqueActivity(activity, 1, "word:${word.id}")
        if (!correct) recordActivity("WRONG_WORD", 1)
        if (counted) incrementTask(if (isReview) "VOCAB_REVIEW" else "VOCAB_NEW", 1)
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
        if (recordUniqueActivity("SENTENCE", 1, stableKey(sentence))) {
            incrementTask("SENTENCE_STUDY", 1)
        }
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

    private suspend fun recordActivity(type: String, amount: Int) {
        markStudyDay()
        database.studyDao().insert(
            StudyActivityEntity(
                date = today(),
                type = type,
                amount = amount,
                durationMinutes = 1,
                createdAt = System.currentTimeMillis()
            )
        )
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
        val count = (task.completedCount + amount).coerceAtMost(task.targetCount)
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
            database.aiDao().clearSentenceCache()
            database.aiDao().clearResponseCache()
            database.aiDao().clearUsage()
        }
        initializeIfNeeded()
    }

    companion object {
        fun today(): String = LocalDate.now().toString()

        private fun stableKey(value: String): String = MessageDigest.getInstance("SHA-256")
            .digest(value.trim().lowercase().toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}
