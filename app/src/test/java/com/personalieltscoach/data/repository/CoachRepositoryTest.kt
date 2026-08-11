package com.personalieltscoach.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.domain.model.PlacementResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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
    fun newWordSessionStopsAtDailyTarget() = runTest {
        repository.initializeIfNeeded()
        repository.savePlacement(
            PlacementResult("A0-A1", 300, "词汇", "基础路线")
        )

        repeat(10) {
            val word = repository.newWords().first()
            repository.answerWord(word, correct = true, isReview = false)
        }

        assertEquals(0, repository.newWords().size)
        assertEquals(10, database.planDao().getTask(CoachRepository.today(), "VOCAB_NEW")?.completedCount)
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
            listOf("trial-041", "trial-042", "trial-043", "trial-044", "trial-045"),
            next.map { it.id }
        )
    }
}
