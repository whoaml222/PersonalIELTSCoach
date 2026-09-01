package com.personalieltscoach.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CoachDatabaseMigrationTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val databaseName = "migration-test.db"

    @After
    fun cleanUp() {
        context.deleteDatabase(databaseName)
    }

    @Test
    fun migratesVersionOneStudyActivitiesWithoutLosingRows() {
        val fresh = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .allowMainThreadQueries()
            .build()
        fresh.openHelper.writableDatabase
        fresh.close()

        SQLiteDatabase.openDatabase(
            context.getDatabasePath(databaseName).absolutePath,
            null,
            SQLiteDatabase.OPEN_READWRITE
        ).use { sqlite ->
            sqlite.execSQL("DROP INDEX IF EXISTS index_study_activities_date_type_referenceKey")
            sqlite.execSQL("DROP INDEX IF EXISTS index_study_activities_date_type")
            sqlite.execSQL("ALTER TABLE study_activities RENAME TO study_activities_v2")
            sqlite.execSQL(
                """
                CREATE TABLE study_activities (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    date TEXT NOT NULL,
                    type TEXT NOT NULL,
                    amount INTEGER NOT NULL,
                    durationMinutes INTEGER NOT NULL,
                    createdAt INTEGER NOT NULL
                )
                """.trimIndent()
            )
            sqlite.execSQL(
                "INSERT INTO study_activities(date, type, amount, durationMinutes, createdAt) " +
                    "VALUES('2026-06-20', 'SENTENCE', 1, 1, 1)"
            )
            sqlite.execSQL(
                "CREATE INDEX index_study_activities_date_type ON study_activities(date, type)"
            )
            sqlite.execSQL("DROP TABLE study_activities_v2")
            downgradeWordsToVersionThree(sqlite)
            sqlite.version = 1
        }

        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(
                CoachDatabase.MIGRATION_1_2,
                CoachDatabase.MIGRATION_2_3,
                CoachDatabase.MIGRATION_3_4
            )
            .allowMainThreadQueries()
            .build()
        val cursor = migrated.openHelper.readableDatabase.query(
            "SELECT referenceKey, amount FROM study_activities"
        )
        cursor.use {
            it.moveToFirst()
            assertEquals("", it.getString(0))
            assertEquals(1, it.getInt(1))
        }

        val sentenceTable = migrated.openHelper.readableDatabase.query(
            "SELECT COUNT(*) FROM sentence_cards"
        )
        sentenceTable.use {
            it.moveToFirst()
            assertEquals(0, it.getInt(0))
        }
        migrated.close()
    }

    @Test
    fun migratesVersionThreeWordsWithProgressAndSource() {
        val fresh = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .allowMainThreadQueries()
            .build()
        fresh.openHelper.writableDatabase
        fresh.close()

        SQLiteDatabase.openDatabase(
            context.getDatabasePath(databaseName).absolutePath,
            null,
            SQLiteDatabase.OPEN_READWRITE
        ).use { sqlite ->
            downgradeWordsToVersionThree(sqlite)
            sqlite.execSQL(
                "INSERT INTO words(word, phonetic, meaning, example, exampleTranslation, level, " +
                    "status, correctStreak, wrongCount, nextReviewAt, lastWrongAt, createdAt, updatedAt) " +
                    "VALUES('book', '/bʊk/', 'n. 书', 'This book is useful.', '这本书很有用。', " +
                    "'NCE1 Lesson 1&2', 'REVIEWING', 2, 1, 12345, 12000, 10000, 11000)"
            )
            sqlite.version = 3
        }

        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(CoachDatabase.MIGRATION_3_4)
            .allowMainThreadQueries()
            .build()
        migrated.openHelper.writableDatabase

        val cursor = migrated.openHelper.readableDatabase.query(
            "SELECT source, status, correctStreak, wrongCount, nextReviewAt FROM words WHERE word = 'book'"
        )
        cursor.use {
            it.moveToFirst()
            assertEquals("NCE1", it.getString(0))
            assertEquals("REVIEWING", it.getString(1))
            assertEquals(2, it.getInt(2))
            assertEquals(1, it.getInt(3))
            assertEquals(12345L, it.getLong(4))
        }
        migrated.openHelper.writableDatabase.execSQL(
            "INSERT INTO words(word, phonetic, meaning, example, exampleTranslation, level, source, " +
                "status, correctStreak, wrongCount, nextReviewAt, lastWrongAt, createdAt, updatedAt) " +
                "VALUES('book', '/bʊk/', 'n. 书', '', '', 'CORE', 'CORE', 'NEW', 0, 0, 0, NULL, 1, 1)"
        )
        val duplicateCursor = migrated.openHelper.readableDatabase.query(
            "SELECT COUNT(*) FROM words WHERE word = 'book'"
        )
        duplicateCursor.use {
            it.moveToFirst()
            assertEquals(2, it.getInt(0))
        }
        migrated.close()
    }

    private fun downgradeWordsToVersionThree(sqlite: SQLiteDatabase) {
        sqlite.execSQL("DROP INDEX IF EXISTS index_words_word_source")
        sqlite.execSQL("DROP INDEX IF EXISTS index_words_source")
        sqlite.execSQL("DROP INDEX IF EXISTS index_words_nextReviewAt")
        sqlite.execSQL("ALTER TABLE words RENAME TO words_v4")
        sqlite.execSQL(
            """
            CREATE TABLE words (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                word TEXT NOT NULL,
                phonetic TEXT NOT NULL,
                meaning TEXT NOT NULL,
                example TEXT NOT NULL,
                exampleTranslation TEXT NOT NULL,
                level TEXT NOT NULL,
                status TEXT NOT NULL,
                correctStreak INTEGER NOT NULL,
                wrongCount INTEGER NOT NULL,
                nextReviewAt INTEGER NOT NULL,
                lastWrongAt INTEGER,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL
            )
            """.trimIndent()
        )
        sqlite.execSQL(
            "INSERT INTO words(id, word, phonetic, meaning, example, exampleTranslation, level, " +
                "status, correctStreak, wrongCount, nextReviewAt, lastWrongAt, createdAt, updatedAt) " +
                "SELECT id, word, phonetic, meaning, example, exampleTranslation, level, status, " +
                "correctStreak, wrongCount, nextReviewAt, lastWrongAt, createdAt, updatedAt FROM words_v4"
        )
        sqlite.execSQL("DROP TABLE words_v4")
        sqlite.execSQL("CREATE UNIQUE INDEX index_words_word ON words(word)")
        sqlite.execSQL("CREATE INDEX index_words_nextReviewAt ON words(nextReviewAt)")
    }
}
