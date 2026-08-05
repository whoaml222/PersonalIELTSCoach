package com.personalieltscoach.domain.service

import com.personalieltscoach.data.local.entity.SentenceCardEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class SentenceReviewSchedulerTest {
    private val now = 1_000_000L
    private val day = 24L * 60L * 60L * 1000L
    private val card = SentenceCardEntity(
        id = "trial-001",
        sentence = "I wake up early.",
        translation = "我很早醒来。",
        chunks = "I wake up~我醒来^early~很早",
        note = "wake up",
        level = "A1",
        category = "日常起居",
        createdAt = 1L
    )

    @Test
    fun `remembered cards progress through spaced intervals`() {
        val first = SentenceReviewScheduler.next(card, SentenceRating.REMEMBERED, now)
        val second = SentenceReviewScheduler.next(first, SentenceRating.REMEMBERED, now)
        val third = SentenceReviewScheduler.next(second, SentenceRating.REMEMBERED, now)
        val fourth = SentenceReviewScheduler.next(third, SentenceRating.REMEMBERED, now)

        assertEquals("LEARNING", first.status)
        assertEquals(now + day, first.nextReviewAt)
        assertEquals(now + 3 * day, second.nextReviewAt)
        assertEquals(now + 7 * day, third.nextReviewAt)
        assertEquals("MASTERED", fourth.status)
        assertEquals(now + 30 * day, fourth.nextReviewAt)
    }

    @Test
    fun `forgotten card returns quickly and resets streak`() {
        val learned = card.copy(correctStreak = 3, status = "REVIEWING", wrongCount = 1)
        val result = SentenceReviewScheduler.next(learned, SentenceRating.FORGOT, now)

        assertEquals("WRONG", result.status)
        assertEquals(0, result.correctStreak)
        assertEquals(2, result.wrongCount)
        assertEquals(now + 4L * 60L * 60L * 1000L, result.nextReviewAt)
    }

    @Test
    fun `fuzzy card is scheduled for tomorrow`() {
        val result = SentenceReviewScheduler.next(card, SentenceRating.FUZZY, now)

        assertEquals("REVIEWING", result.status)
        assertEquals(now + day, result.nextReviewAt)
    }
}
