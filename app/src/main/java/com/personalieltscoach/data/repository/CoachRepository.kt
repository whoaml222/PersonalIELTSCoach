package com.personalieltscoach.data.repository

import androidx.room.withTransaction
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.data.seed.Nce1WordPack
import com.personalieltscoach.data.seed.Paul1000SentencePack
import com.personalieltscoach.data.seed.SeedData
import com.personalieltscoach.domain.model.PlacementResult
import com.personalieltscoach.domain.service.ReviewScheduler
import com.personalieltscoach.domain.service.SentencePackStats
import com.personalieltscoach.domain.service.SentenceRating
import com.personalieltscoach.domain.service.SentenceReviewScheduler
import com.personalieltscoach.domain.service.StreakCalculator
import com.personalieltscoach.domain.service.TextSegmenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
    val sentencePackStats: Flow<SentencePackStats> = combine(
        database.sentenceCardDao().observeCount(),
        database.sentenceCardDao().observeStartedCount(),
        database.sentenceCardDao().observeMasteredCount()
    ) { total, started, mastered ->
        SentencePackStats(total = total, started = started, mastered = mastered)
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
            val sentenceCards = Paul1000SentencePack.cards(now, nceWords)
            val paulWords = Paul1000SentencePack.words(now, nceWords)
            // INSERT IGNORE turns every app upgrade into an incremental content
            // merge. Existing progress is retained while newly bundled packs are
            // added to accounts that have already studied the original 100 words.
            database.wordDao().insertAll(
                SeedData.words(now) + nceWords + paulWords
            )
            // If a learner already knew the same word in the original core pack,
            // do not show it again as a brand-new NCE word after upgrading.
            database.wordDao().inheritLegacyProgressForNce()
            // Content can improve independently from review progress. Update only
            // the bundled NCE fields, leaving ids, status, streaks, wrong answers,
            // review dates and timestamps untouched for existing learners.
            nceWords.forEach { word ->
                database.wordDao().updateNceLearningContent(
                    word = word.word,
                    phonetic = word.phonetic,
                    meaning = word.meaning,
                    example = word.example,
                    exampleTranslation = word.exampleTranslation,
                    level = word.level
                )
            }
            paulWords.forEach { word ->
                database.wordDao().updatePaulLearningContent(
                    word = word.word,
                    phonetic = word.phonetic,
                    meaning = word.meaning,
                    example = word.example,
                    exampleTranslation = word.exampleTranslation,
                    level = word.level
                )
            }
            if (database.contentDao().questionCount() == 0) {
                database.contentDao().insertQuestions(SeedData.questions(json))
            }
            if (database.contentDao().readingCount() == 0) {
                database.contentDao().insertReadings(SeedData.readings(now))
            }
            // The Paul1000 pack replaces the old 300-card trial. Old progress cannot
            // be mapped safely to different sentences, so only that retired pack is removed.
            database.sentenceCardDao().deleteLegacyTrialPack()
            database.sentenceCardDao().insertAll(sentenceCards)
            sentenceCards.forEach { card ->
                database.sentenceCardDao().updatePaulContent(
                    id = card.id,
                    sentence = card.sentence,
                    translation = card.translation,
                    chunks = card.chunks,
                    note = card.note,
                    level = card.level,
                    category = card.category
                )
            }
            // v1.5 shortens the clearly explained mastery goal from four confident
            // recalls to three, so existing learners receive the corrected status too.
            database.sentenceCardDao().promoteEligibleToMastered()
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
            if (newWordTask != null && newWordTask.targetCount != NEW_WORD_DAILY_GOAL) {
                database.withTransaction {
                    database.planDao().upsertTask(
                        newWordTask.copy(
                            description = "$NEW_WORD_DAILY_GOAL 个新概念英语1词汇，完成后可继续",
                            targetCount = NEW_WORD_DAILY_GOAL,
                            completed = newWordTask.completedCount >= NEW_WORD_DAILY_GOAL
                        )
                    )
                    val completedTasks = database.planDao().getTasks(date).count { it.completed }
                    database.planDao().upsertPlan(existingPlan.copy(completedCount = completedTasks))
                }
            }
            return
        }
        val settings = settingsRepository.current()
        val a1Plus = profile.currentLevel != "A0-A1"
        val reviewTarget = if (a1Plus) maxOf(settings.dailyReviewWords, 30) else settings.dailyReviewWords
        val sentenceTarget = if (a1Plus) maxOf(settings.dailySentences, 8) else settings.dailySentences
        val writingTarget = if (a1Plus) 5 else 3
        val now = System.currentTimeMillis()
        val tasks = listOf(
            StudyTaskEntity(date = date, type = "VOCAB_REVIEW", title = "复习旧单词", description = "$reviewTarget 个到期词：碎片句子与新概念各半", targetCount = reviewTarget),
            StudyTaskEntity(date = date, type = "VOCAB_NEW", title = "学习新单词", description = "$NEW_WORD_DAILY_GOAL 个新概念英语1词汇，完成后可继续", targetCount = NEW_WORD_DAILY_GOAL),
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

    suspend fun newWords(continueAfterGoal: Boolean = false): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_NEW") ?: return emptyList()
        val remainingGoal = (task.targetCount - task.completedCount).coerceAtLeast(0)
        val limit = if (remainingGoal > 0) remainingGoal else if (continueAfterGoal) EXTRA_WORD_BATCH else 0
        if (limit == 0) return emptyList()
        return database.wordDao().getNewBySource(WordSource.NCE1, limit)
    }

    suspend fun dueWords(now: Long = System.currentTimeMillis()): List<WordItemEntity> {
        val task = database.planDao().getTask(today(), "VOCAB_REVIEW") ?: return emptyList()
        val limit = (task.targetCount - task.completedCount).coerceAtLeast(0)
        if (limit == 0) return emptyList()
        val start = dayStart(now)
        val nceTarget = (limit + 1) / 2
        val paulTarget = limit / 2
        val nce = database.wordDao().getDueBySource(WordSource.NCE1, now, start, nceTarget)
        val paul = database.wordDao().getDueBySource(WordSource.PAUL1000, now, start, paulTarget)

        val selected = interleave(nce, paul).toMutableList()
        var remaining = limit - selected.size
        if (remaining > 0) {
            val selectedIds = selected.mapTo(mutableSetOf()) { it.id }
            val extraNce = database.wordDao()
                .getDueBySource(WordSource.NCE1, now, start, limit)
                .filterNot { it.id in selectedIds }
            val extraPaul = database.wordDao()
                .getDueBySource(WordSource.PAUL1000, now, start, limit)
                .filterNot { it.id in selectedIds }
            (extraNce + extraPaul).take(remaining).forEach {
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

    suspend fun sentenceSession(
        limit: Int,
        now: Long = System.currentTimeMillis()
    ): List<SentenceCardEntity> {
        val safeLimit = limit.coerceIn(1, 30)
        val dayStart = Instant.ofEpochMilli(now)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        // Keep progress moving: reserve most of every session for unseen cards while
        // still including a small, useful review set. Previously an overdue queue could
        // completely block new cards and make the learner appear stuck.
        val reviewTarget = (safeLimit / 3).coerceAtLeast(1)
        val due = database.sentenceCardDao().getDue(now, dayStart, reviewTarget)
        val fresh = database.sentenceCardDao().getNew(safeLimit - due.size)
        if (due.size + fresh.size >= safeLimit) return (fresh + due).distinctBy { it.id }

        val selectedIds = (due + fresh).mapTo(mutableSetOf()) { it.id }
        val extraDue = database.sentenceCardDao()
            .getDue(now, dayStart, safeLimit)
            .filterNot { it.id in selectedIds }
            .take(safeLimit - due.size - fresh.size)
        return (fresh + due + extraDue).distinctBy { it.id }
    }

    suspend fun answerSentence(card: SentenceCardEntity, rating: SentenceRating) {
        database.withTransaction {
            database.sentenceCardDao().upsert(SentenceReviewScheduler.next(card, rating))
            Paul1000SentencePack.focusWord(card.id)?.let { focusWord ->
                database.wordDao().findBySource(focusWord, WordSource.PAUL1000)?.let { word ->
                    val wordUpdate = ReviewScheduler.next(
                        correct = rating != SentenceRating.FORGOT,
                        currentStreak = word.correctStreak,
                        wrongCount = word.wrongCount,
                        now = System.currentTimeMillis()
                    )
                    database.wordDao().upsert(
                        word.copy(
                            status = wordUpdate.status,
                            correctStreak = wordUpdate.correctStreak,
                            wrongCount = wordUpdate.wrongCount,
                            nextReviewAt = wordUpdate.nextReviewAt,
                            lastWrongAt = if (rating == SentenceRating.FORGOT) {
                                System.currentTimeMillis()
                            } else {
                                word.lastWrongAt
                            },
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
            }
            val date = today()
            val referenceKey = "sentence-card:${card.id}"
            if (database.studyDao().countByReference(date, "SENTENCE", referenceKey) == 0) {
                markStudyDay()
                database.studyDao().insert(
                    StudyActivityEntity(
                        date = date,
                        type = "SENTENCE",
                        referenceKey = referenceKey,
                        amount = 1,
                        durationMinutes = 1,
                        createdAt = System.currentTimeMillis()
                    )
                )
                incrementTask("SENTENCE_STUDY", 1)
            }
        }
    }

    suspend fun answerWord(word: WordItemEntity, correct: Boolean, isReview: Boolean) {
        val now = System.currentTimeMillis()
        val update = ReviewScheduler.next(correct, word.correctStreak, word.wrongCount, now)
        database.withTransaction {
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
                incrementTask(if (isReview) "VOCAB_REVIEW" else "VOCAB_NEW", 1)
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
            database.sentenceCardDao().clear()
            database.aiDao().clearSentenceCache()
            database.aiDao().clearResponseCache()
            database.aiDao().clearUsage()
        }
        initializeIfNeeded()
    }

    companion object {
        const val NEW_WORD_DAILY_GOAL = 20
        private const val EXTRA_WORD_BATCH = 20

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
