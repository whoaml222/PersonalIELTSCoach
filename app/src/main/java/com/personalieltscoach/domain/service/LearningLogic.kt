package com.personalieltscoach.domain.service

import com.personalieltscoach.domain.model.PlacementResult
import java.util.Calendar
import java.time.LocalDate

object PlacementEvaluator {
    fun evaluate(correct: Int): PlacementResult = when {
        correct <= 10 -> PlacementResult(
            "A0-A1", 300, "词汇、句子结构、听力",
            "先学习基础高频词和简单句型"
        )
        correct <= 19 -> PlacementResult(
            "A1-A2", 800, "中等词汇、语法表达、听力",
            "巩固高频词，并开始短文阅读与段落表达"
        )
        correct <= 25 -> PlacementResult(
            "A2-B1", 1500, "复杂句、连贯表达、听力",
            "开始接触雅思基础话题与段落写作"
        )
        else -> PlacementResult(
            "B1+", 2500, "雅思题型、复杂表达、学术词汇",
            "进入系统雅思听说读写训练"
        )
    }
}

data class ReviewUpdate(
    val status: String,
    val correctStreak: Int,
    val wrongCount: Int,
    val nextReviewAt: Long
)

object ReviewScheduler {
    private const val DAY_MS = 24L * 60L * 60L * 1000L

    fun next(
        correct: Boolean,
        currentStreak: Int,
        wrongCount: Int,
        now: Long = System.currentTimeMillis()
    ): ReviewUpdate {
        if (!correct) {
            return ReviewUpdate(
                status = "WRONG",
                correctStreak = 0,
                wrongCount = wrongCount + 1,
                nextReviewAt = startOfDay(now) + DAY_MS
            )
        }
        val streak = currentStreak + 1
        val delayDays = when (streak) {
            1 -> 1
            2 -> 3
            3 -> 7
            else -> 30
        }
        return ReviewUpdate(
            status = if (streak >= 4) "MASTERED" else if (streak == 1) "LEARNING" else "REVIEWING",
            correctStreak = streak.coerceAtMost(4),
            wrongCount = (wrongCount - 1).coerceAtLeast(0),
            nextReviewAt = startOfDay(now) + delayDays * DAY_MS
        )
    }

    private fun startOfDay(time: Long): Long = Calendar.getInstance().run {
        timeInMillis = time
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        timeInMillis
    }
}

object TextSegmenter {
    fun sentences(text: String): List<String> =
        text.trim()
            .split(Regex("(?<=[.!?。！？])\\s+|\\n+"))
            .map(String::trim)
            .filter(String::isNotBlank)

    fun words(text: String): List<String> =
        Regex("[A-Za-z]+(?:['’-][A-Za-z]+)*")
            .findAll(text)
            .map { it.value }
            .toList()
}

object StreakCalculator {
    fun next(currentStreak: Int, lastStudyDate: LocalDate, studyDate: LocalDate): Int = when {
        studyDate == lastStudyDate -> currentStreak.coerceAtLeast(1)
        lastStudyDate == studyDate.minusDays(1) -> currentStreak.coerceAtLeast(1) + 1
        else -> 1
    }
}
