package com.personalieltscoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.personalieltscoach.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun observe(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun get(): UserProfileEntity?

    @Upsert
    suspend fun upsert(profile: UserProfileEntity)

    @Query("DELETE FROM user_profile")
    suspend fun clear()
}

@Dao
interface WordDao {
    @Query("SELECT * FROM words ORDER BY id")
    fun observeAll(): Flow<List<WordItemEntity>>

    @Query("SELECT * FROM words WHERE wrongCount > 0 ORDER BY wrongCount DESC, lastWrongAt DESC")
    fun observeWrong(): Flow<List<WordItemEntity>>

    @Query("SELECT * FROM words WHERE status = 'NEW' ORDER BY id LIMIT :limit")
    suspend fun getNew(limit: Int): List<WordItemEntity>

    @Query("SELECT * FROM words WHERE status != 'NEW' AND status != 'MASTERED' AND nextReviewAt <= :now ORDER BY nextReviewAt LIMIT :limit")
    suspend fun getDue(now: Long, limit: Int): List<WordItemEntity>

    @Query("SELECT * FROM words WHERE LOWER(word) = LOWER(:word) LIMIT 1")
    suspend fun find(word: String): WordItemEntity?

    @Query("SELECT COUNT(*) FROM words")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM words WHERE status = :status")
    fun observeStatusCount(status: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM words WHERE wrongCount > 0")
    fun observeWrongCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<WordItemEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordItemEntity): Long

    @Upsert
    suspend fun upsert(word: WordItemEntity)

    @Query("DELETE FROM words")
    suspend fun clear()
}

@Dao
interface PlanDao {
    @Query("SELECT * FROM daily_plans WHERE date = :date LIMIT 1")
    fun observePlan(date: String): Flow<DailyPlanEntity?>

    @Query("SELECT * FROM daily_plans WHERE date = :date LIMIT 1")
    suspend fun getPlan(date: String): DailyPlanEntity?

    @Query("SELECT * FROM study_tasks WHERE date = :date ORDER BY id")
    fun observeTasks(date: String): Flow<List<StudyTaskEntity>>

    @Query("SELECT * FROM study_tasks WHERE date = :date ORDER BY id")
    suspend fun getTasks(date: String): List<StudyTaskEntity>

    @Query("SELECT * FROM study_tasks WHERE date = :date AND type = :type LIMIT 1")
    suspend fun getTask(date: String, type: String): StudyTaskEntity?

    @Upsert
    suspend fun upsertPlan(plan: DailyPlanEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<StudyTaskEntity>)

    @Upsert
    suspend fun upsertTask(task: StudyTaskEntity)

    @Query("DELETE FROM daily_plans")
    suspend fun clearPlans()

    @Query("DELETE FROM study_tasks")
    suspend fun clearTasks()
}

@Dao
interface ContentDao {
    @Query("SELECT * FROM placement_questions ORDER BY id")
    suspend fun questions(): List<PlacementQuestionEntity>

    @Query("SELECT * FROM reading_texts ORDER BY id")
    fun observeReadings(): Flow<List<ReadingTextEntity>>

    @Query("SELECT COUNT(*) FROM placement_questions")
    suspend fun questionCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(items: List<PlacementQuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReadings(items: List<ReadingTextEntity>)

    @Query("SELECT COUNT(*) FROM reading_texts")
    suspend fun readingCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSentence(item: SavedSentenceEntity)

    @Query("DELETE FROM saved_sentences")
    suspend fun clearSavedSentences()
}

@Dao
interface AiDao {
    @Query("SELECT * FROM sentence_analysis_cache WHERE sentenceHash = :hash LIMIT 1")
    suspend fun sentenceCache(hash: String): SentenceAnalysisCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSentenceCache(item: SentenceAnalysisCacheEntity)

    @Query("SELECT * FROM ai_response_cache WHERE requestHash = :hash LIMIT 1")
    suspend fun responseCache(hash: String): AiResponseCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResponseCache(item: AiResponseCacheEntity)

    @Query("SELECT COALESCE(SUM(callCount), 0) FROM api_usage_records WHERE date = :date")
    fun observeUsage(date: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(callCount), 0) FROM api_usage_records WHERE date = :date")
    suspend fun usage(date: String): Int

    @Query("SELECT * FROM api_usage_records WHERE date = :date AND provider = :provider AND model = :model AND featureType = :feature LIMIT 1")
    suspend fun usageRecord(date: String, provider: String, model: String, feature: String): ApiUsageRecordEntity?

    @Upsert
    suspend fun upsertUsage(item: ApiUsageRecordEntity)

    @Query("DELETE FROM sentence_analysis_cache")
    suspend fun clearSentenceCache()

    @Query("DELETE FROM ai_response_cache")
    suspend fun clearResponseCache()

    @Query("DELETE FROM api_usage_records")
    suspend fun clearUsage()
}

@Dao
interface WritingDao {
    @Query("SELECT * FROM writing_records ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<WritingRecordEntity>>

    @Insert
    suspend fun insert(item: WritingRecordEntity)

    @Query("SELECT COUNT(*) FROM writing_records")
    fun observeCount(): Flow<Int>

    @Query("DELETE FROM writing_records")
    suspend fun clear()
}

data class ActivityTotal(val type: String, val amount: Int)

@Dao
interface StudyDao {
    @Insert
    suspend fun insert(item: StudyActivityEntity): Long

    @Query("SELECT COUNT(*) FROM study_activities WHERE date = :date AND type = :type AND referenceKey = :referenceKey")
    suspend fun countByReference(date: String, type: String, referenceKey: String): Int

    @Query("SELECT type, COALESCE(SUM(amount), 0) AS amount FROM study_activities WHERE date = :date GROUP BY type")
    fun observeTotals(date: String): Flow<List<ActivityTotal>>

    @Query("SELECT COALESCE(SUM(durationMinutes), 0) FROM study_activities WHERE date = :date")
    fun observeMinutes(date: String): Flow<Int>

    @Query("DELETE FROM study_activities")
    suspend fun clear()
}
