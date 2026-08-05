package com.personalieltscoach.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Long = 1,
    val currentLevel: String,
    val targetScore: Float = 7.0f,
    val estimatedVocabulary: Int,
    val weakSkills: String,
    val createdAt: Long,
    val streakDays: Int = 1,
    val lastStudyDate: Long
)

@Entity(
    tableName = "words",
    indices = [Index(value = ["word"], unique = true), Index(value = ["nextReviewAt"])]
)
data class WordItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String,
    val phonetic: String,
    val meaning: String,
    val example: String,
    val exampleTranslation: String,
    val level: String,
    val status: String = "NEW",
    val correctStreak: Int = 0,
    val wrongCount: Int = 0,
    val nextReviewAt: Long = 0,
    val lastWrongAt: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "daily_plans", indices = [Index(value = ["date"], unique = true)])
data class DailyPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val level: String,
    val completedCount: Int = 0,
    val totalCount: Int,
    val createdAt: Long
)

@Entity(tableName = "study_tasks", indices = [Index(value = ["date", "type"], unique = true)])
data class StudyTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val type: String,
    val title: String,
    val description: String,
    val targetCount: Int,
    val completedCount: Int = 0,
    val completed: Boolean = false
)

@Entity(
    tableName = "sentence_analysis_cache",
    indices = [Index(value = ["sentenceHash"], unique = true)]
)
data class SentenceAnalysisCacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sentenceHash: String,
    val sentence: String,
    val translation: String,
    val wordExplanation: String,
    val phraseExplanation: String,
    val sentenceStructure: String,
    val grammarPoint: String,
    val imitationExample: String,
    val imitationExampleTranslation: String,
    val provider: String,
    val model: String,
    val createdAt: Long
)

@Entity(tableName = "ai_response_cache", indices = [Index(value = ["requestHash"], unique = true)])
data class AiResponseCacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val requestHash: String,
    val featureType: String,
    val responseJson: String,
    val provider: String,
    val model: String,
    val createdAt: Long
)

@Entity(tableName = "reading_texts")
data class ReadingTextEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val level: String,
    val wordCount: Int,
    val createdAt: Long
)

@Entity(tableName = "writing_records")
data class WritingRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val promptChinese: String,
    val userText: String,
    val correctedText: String,
    val explanation: String,
    val level: String,
    val createdAt: Long
)

@Entity(
    tableName = "api_usage_records",
    indices = [Index(value = ["date", "provider", "model", "featureType"], unique = true)]
)
data class ApiUsageRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val provider: String,
    val model: String,
    val featureType: String,
    val callCount: Int,
    val createdAt: Long
)

@Entity(tableName = "placement_questions")
data class PlacementQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val question: String,
    val options: String,
    val answer: String,
    val type: String,
    val level: String
)

@Entity(
    tableName = "study_activities",
    indices = [
        Index(value = ["date", "type"]),
        Index(value = ["date", "type", "referenceKey"])
    ]
)
data class StudyActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val type: String,
    @ColumnInfo(defaultValue = "''")
    val referenceKey: String = "",
    val amount: Int = 1,
    val durationMinutes: Int = 0,
    val createdAt: Long
)

@Entity(tableName = "saved_sentences", indices = [Index(value = ["sentence"], unique = true)])
data class SavedSentenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sentence: String,
    val translation: String,
    val createdAt: Long
)

@Entity(
    tableName = "sentence_cards",
    indices = [
        Index(value = ["status"]),
        Index(value = ["nextReviewAt"]),
        Index(value = ["level"]),
        Index(value = ["category"])
    ]
)
data class SentenceCardEntity(
    @PrimaryKey val id: String,
    val sentence: String,
    val translation: String,
    val chunks: String,
    val note: String,
    val level: String,
    val category: String,
    val status: String = "NEW",
    val correctStreak: Int = 0,
    val wrongCount: Int = 0,
    val nextReviewAt: Long = 0,
    val lastStudiedAt: Long? = null,
    val createdAt: Long
)
