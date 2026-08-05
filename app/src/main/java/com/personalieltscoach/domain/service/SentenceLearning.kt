package com.personalieltscoach.domain.service

import com.personalieltscoach.data.local.entity.SentenceCardEntity

data class SentenceChunk(val english: String, val chinese: String)

object SentenceChunkCodec {
    private const val CHUNK_SEPARATOR = "^"
    private const val MEANING_SEPARATOR = "~"

    fun decode(value: String): List<SentenceChunk> = value
        .split(CHUNK_SEPARATOR)
        .map(String::trim)
        .filter(String::isNotBlank)
        .mapNotNull { item ->
            val separator = item.indexOf(MEANING_SEPARATOR)
            if (separator <= 0 || separator >= item.lastIndex) return@mapNotNull null
            SentenceChunk(
                english = item.substring(0, separator).trim(),
                chinese = item.substring(separator + 1).trim()
            )
        }

    fun encode(chunks: List<SentenceChunk>): String = chunks.joinToString(CHUNK_SEPARATOR) {
        "${it.english}$MEANING_SEPARATOR${it.chinese}"
    }
}

enum class SentenceRating { REMEMBERED, FUZZY, FORGOT }

object SentenceReviewScheduler {
    private const val HOUR_MS = 60L * 60L * 1000L
    private const val DAY_MS = 24L * HOUR_MS

    fun next(
        card: SentenceCardEntity,
        rating: SentenceRating,
        now: Long = System.currentTimeMillis()
    ): SentenceCardEntity {
        val nextStreak = when (rating) {
            SentenceRating.REMEMBERED -> card.correctStreak + 1
            SentenceRating.FUZZY -> card.correctStreak.coerceAtMost(1)
            SentenceRating.FORGOT -> 0
        }
        val delay = when (rating) {
            SentenceRating.FORGOT -> 4 * HOUR_MS
            SentenceRating.FUZZY -> DAY_MS
            SentenceRating.REMEMBERED -> when (nextStreak) {
                1 -> DAY_MS
                2 -> 3 * DAY_MS
                3 -> 7 * DAY_MS
                else -> 30 * DAY_MS
            }
        }
        return card.copy(
            status = when {
                rating == SentenceRating.FORGOT -> "WRONG"
                nextStreak >= 4 -> "MASTERED"
                nextStreak == 1 -> "LEARNING"
                else -> "REVIEWING"
            },
            correctStreak = nextStreak.coerceAtMost(4),
            wrongCount = when (rating) {
                SentenceRating.FORGOT -> card.wrongCount + 1
                SentenceRating.FUZZY -> card.wrongCount
                SentenceRating.REMEMBERED -> (card.wrongCount - 1).coerceAtLeast(0)
            },
            nextReviewAt = now + delay,
            lastStudiedAt = now
        )
    }
}

data class SentencePackStats(
    val total: Int = 0,
    val started: Int = 0,
    val mastered: Int = 0
)
