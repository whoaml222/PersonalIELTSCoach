package com.personalieltscoach.domain.service

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import com.personalieltscoach.update.VersionComparator

class LearningLogicTest {
    @Test
    fun placementLevelsFollowScoreBands() {
        assertEquals("A0-A1", PlacementEvaluator.evaluate(8).level)
        assertEquals("A1-A2", PlacementEvaluator.evaluate(15).level)
        assertEquals("A2-B1", PlacementEvaluator.evaluate(23).level)
        assertEquals("B1+", PlacementEvaluator.evaluate(29).level)
    }

    @Test
    fun reviewSchedulerMastersAfterFourCorrectAnswers() {
        val now = 1_700_000_000_000L
        val first = ReviewScheduler.next(true, 0, 0, now)
        val second = ReviewScheduler.next(true, first.correctStreak, 0, now)
        val third = ReviewScheduler.next(true, second.correctStreak, 0, now)
        val fourth = ReviewScheduler.next(true, third.correctStreak, 0, now)
        assertEquals("MASTERED", fourth.status)
        assertEquals(4, fourth.correctStreak)
        assertTrue(fourth.nextReviewAt > now)
    }

    @Test
    fun wrongAnswerReturnsTomorrowAndIncrementsCount() {
        val result = ReviewScheduler.next(false, currentStreak = 3, wrongCount = 2)
        assertEquals("WRONG", result.status)
        assertEquals(0, result.correctStreak)
        assertEquals(3, result.wrongCount)
    }

    @Test
    fun correctReviewClearsOneRecordedMistake() {
        val result = ReviewScheduler.next(true, currentStreak = 0, wrongCount = 1)
        assertEquals("LEARNING", result.status)
        assertEquals(0, result.wrongCount)
    }

    @Test
    fun readingTextSplitsLocally() {
        val sentences = TextSegmenter.sentences("I read books. It makes me happy! Do you read?")
        assertEquals(3, sentences.size)
        assertEquals(10, TextSegmenter.words("I read books. It makes me happy! Do you read?").size)
        assertEquals(
            listOf("I'm", "Li", "Ming"),
            TextSegmenter.words("I'm Li Ming.")
        )
    }

    @Test
    fun streakOnlyContinuesOnConsecutiveStudyDays() {
        val day = LocalDate.of(2026, 6, 21)
        assertEquals(5, StreakCalculator.next(5, day, day))
        assertEquals(6, StreakCalculator.next(5, day, day.plusDays(1)))
        assertEquals(1, StreakCalculator.next(5, day, day.plusDays(2)))
    }

    @Test
    fun versionComparisonHandlesPrefixesAndMultipleDigits() {
        assertTrue(VersionComparator.isNewer("v1.0.10", "1.0.2"))
        assertTrue(VersionComparator.isNewer("2.0", "1.99.99"))
        assertEquals(0, VersionComparator.compare("v1.1.0", "1.1"))
        assertTrue(!VersionComparator.isNewer("1.0.1", "1.1.0"))
    }

    @Test
    fun wordTypesIncludeChineseExplanationWithoutChangingDefinition() {
        assertEquals("v. 动词 · 需要", WordPresentation.meaningWithChineseType("v. 需要"))
        assertEquals(
            "modal v. 情态动词 · 能；可以",
            WordPresentation.meaningWithChineseType("modal v. 能；可以")
        )
        assertEquals("短语释义", WordPresentation.meaningWithChineseType("短语释义"))
    }
}
