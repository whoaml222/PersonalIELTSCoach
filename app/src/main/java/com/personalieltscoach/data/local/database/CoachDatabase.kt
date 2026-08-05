package com.personalieltscoach.data.local.database

import android.content.Context
import androidx.room.migration.Migration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.personalieltscoach.data.local.dao.*
import com.personalieltscoach.data.local.entity.*

@Database(
    entities = [
        UserProfileEntity::class,
        WordItemEntity::class,
        DailyPlanEntity::class,
        StudyTaskEntity::class,
        SentenceAnalysisCacheEntity::class,
        AiResponseCacheEntity::class,
        ReadingTextEntity::class,
        WritingRecordEntity::class,
        ApiUsageRecordEntity::class,
        PlacementQuestionEntity::class,
        StudyActivityEntity::class,
        SavedSentenceEntity::class,
        SentenceCardEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class CoachDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun wordDao(): WordDao
    abstract fun planDao(): PlanDao
    abstract fun contentDao(): ContentDao
    abstract fun aiDao(): AiDao
    abstract fun writingDao(): WritingDao
    abstract fun studyDao(): StudyDao
    abstract fun sentenceCardDao(): SentenceCardDao

    companion object {
        fun create(context: Context): CoachDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                CoachDatabase::class.java,
                "personal_ielts_coach.db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .build()

        internal val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE study_activities ADD COLUMN referenceKey TEXT NOT NULL DEFAULT ''"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_study_activities_date_type_referenceKey " +
                        "ON study_activities(date, type, referenceKey)"
                )
            }
        }


        internal val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS sentence_cards (" +
                        "id TEXT NOT NULL, sentence TEXT NOT NULL, translation TEXT NOT NULL, " +
                        "chunks TEXT NOT NULL, note TEXT NOT NULL, level TEXT NOT NULL, " +
                        "category TEXT NOT NULL, status TEXT NOT NULL, correctStreak INTEGER NOT NULL, " +
                        "wrongCount INTEGER NOT NULL, nextReviewAt INTEGER NOT NULL, " +
                        "lastStudiedAt INTEGER, createdAt INTEGER NOT NULL, PRIMARY KEY(id))"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_sentence_cards_status ON sentence_cards(status)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_sentence_cards_nextReviewAt ON sentence_cards(nextReviewAt)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_sentence_cards_level ON sentence_cards(level)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_sentence_cards_category ON sentence_cards(category)"
                )
            }
        }
    }
}
