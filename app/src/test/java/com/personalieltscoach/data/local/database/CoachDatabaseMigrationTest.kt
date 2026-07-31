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
            sqlite.version = 1
        }

        val migrated = Room.databaseBuilder(context, CoachDatabase::class.java, databaseName)
            .addMigrations(CoachDatabase.MIGRATION_1_2)
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
        migrated.close()
    }
}
