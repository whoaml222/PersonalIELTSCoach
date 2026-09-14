package com.personalieltscoach.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.data.repository.CoachRepository
import com.personalieltscoach.data.repository.CoachSettings
import com.personalieltscoach.data.repository.SettingsProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
            dropReadingTables(sqlite)
            sqlite.version = 1
        }

        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(
                CoachDatabase.MIGRATION_1_2,
                CoachDatabase.MIGRATION_2_3,
                CoachDatabase.MIGRATION_3_4,
                CoachDatabase.MIGRATION_4_5
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
            dropReadingTables(sqlite)
            sqlite.execSQL(
                "INSERT INTO words(word, phonetic, meaning, example, exampleTranslation, level, " +
                    "status, correctStreak, wrongCount, nextReviewAt, lastWrongAt, createdAt, updatedAt) " +
                    "VALUES('book', '/bʊk/', 'n. 书', 'This book is useful.', '这本书很有用。', " +
                    "'NCE1 Lesson 1&2', 'REVIEWING', 2, 1, 12345, 12000, 10000, 11000)"
            )
            sqlite.version = 3
        }

        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(CoachDatabase.MIGRATION_3_4, CoachDatabase.MIGRATION_4_5)
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

    @Test
    @Config(sdk = [26, 28, 34])
    fun versionFourUpgradeAndContentRefreshPreserveLearningRecords() = runBlocking {
        val oldWords = listOf(WordSource.NCE1, WordSource.PAUL1000).mapIndexed { index, source ->
            WordItemEntity(id = 41L + index, word = if (index == 0) "blunt" else "book",
                phonetic = if (index == 0) "/blʌnt/" else "/bʊk/", meaning = if (index == 0) "adj. 钝的" else "n. 书",
                example = if (index == 0) "We need a blunt box for the spare parts." else "Please remember the word book.",
                exampleTranslation = "错误的旧例句",
                level = if (source == WordSource.NCE1) "NCE1 Lesson 21&22" else "Paul1000",
                source = source, status = if (index == 0) "REVIEWING" else "MASTERED",
                correctStreak = 2 + index, wrongCount = 1, nextReviewAt = 90000,
                lastWrongAt = 10000, createdAt = 1000, updatedAt = 20000)
        }
        val custom = oldWords.first().copy(id = 43, word = "my-custom-word", source = WordSource.CORE,
            example = "My personal example.", exampleTranslation = "我自己添加的内容")
        val profile = UserProfileEntity(currentLevel = "A0-A1", estimatedVocabulary = 300,
            weakSkills = "听力", createdAt = 1000, lastStudyDate = 20000, streakDays = 8)
        val date = CoachRepository.today()
        val task = StudyTaskEntity(id = 71, date = date, type = "VOCAB_NEW", title = "学习新单词",
            description = "旧说明", targetCount = 20, completedCount = 8)
        val fresh = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .allowMainThreadQueries().build()
        fresh.wordDao().insertAll(oldWords + custom)
        fresh.userProfileDao().upsert(profile)
        fresh.planDao().upsertPlan(DailyPlanEntity(id = 70, date = date, level = "A0-A1", totalCount = 5, createdAt = 1000))
        fresh.planDao().upsertTask(task)
        fresh.studyDao().insert(StudyActivityEntity(id = 72, date = date, type = "NEW_WORD",
            referenceKey = "old-study-reference", amount = 8, durationMinutes = 12, createdAt = 20000))
        fresh.contentDao().saveSentence(SavedSentenceEntity(id = 73, sentence = "Keep my saved sentence.",
            translation = "保留我的收藏。", createdAt = 20000))
        fresh.close()

        SQLiteDatabase.openDatabase(context.getDatabasePath(databaseName).absolutePath, null,
            SQLiteDatabase.OPEN_READWRITE).use { sqlite ->
            // The shipped v1.6.3 database did not have any reading-course tables.
            dropReadingTables(sqlite)
            sqlite.version = 4
        }
        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(CoachDatabase.MIGRATION_4_5).allowMainThreadQueries().build()
        try {
            val repository = CoachRepository(migrated, object : SettingsProvider {
                override suspend fun current() = CoachSettings()
            }, Json { ignoreUnknownKeys = true })
            repository.initializeIfNeeded()
            // Reopening the app must be idempotent, including content corrections.
            repository.initializeIfNeeded()
            oldWords.forEach { old ->
                val actual = requireNotNull(migrated.wordDao().getById(old.id))
                assertEquals(old.source, actual.source)
                assertEquals(old.status, actual.status)
                assertEquals(old.correctStreak, actual.correctStreak)
                assertEquals(old.wrongCount, actual.wrongCount)
                assertEquals(old.nextReviewAt, actual.nextReviewAt)
                assertEquals(old.lastWrongAt, actual.lastWrongAt)
                assertEquals(old.createdAt, actual.createdAt)
                assertEquals(old.updatedAt, actual.updatedAt)
                assertFalse("${old.source} must replace its old example", actual.example == old.example)
                assertTrue(actual.example.contains(if (old.word == "blunt") "knife" else "book"))
            }
            assertEquals(custom, migrated.wordDao().getById(custom.id))
            assertEquals(profile, migrated.userProfileDao().get())
            val savedTask = requireNotNull(migrated.planDao().getTask(date, "VOCAB_NEW"))
            assertEquals(task.id, savedTask.id)
            assertEquals(8, savedTask.completedCount)
            assertEquals(20, savedTask.targetCount)
            migrated.openHelper.readableDatabase.query(
                "SELECT referenceKey, amount, durationMinutes FROM study_activities WHERE id = 72"
            ).use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals("old-study-reference", cursor.getString(0))
                assertEquals(8, cursor.getInt(1))
                assertEquals(12, cursor.getInt(2))
            }
            migrated.openHelper.readableDatabase.query("SELECT sentence FROM saved_sentences WHERE id = 73").use {
                assertTrue(it.moveToFirst())
                assertEquals("Keep my saved sentence.", it.getString(0))
            }
            listOf("reading_courses", "reading_progress", "reading_vocabulary").forEach { table ->
                migrated.openHelper.readableDatabase.query("SELECT COUNT(*) FROM $table").use {
                    assertTrue(it.moveToFirst())
                    assertEquals(0, it.getInt(0))
                }
            }
        } finally {
            migrated.close()
        }
    }

    private fun dropReadingTables(sqlite: SQLiteDatabase) {
        sqlite.execSQL("DROP TABLE IF EXISTS reading_courses")
        sqlite.execSQL("DROP TABLE IF EXISTS reading_progress")
        sqlite.execSQL("DROP TABLE IF EXISTS reading_vocabulary")
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
