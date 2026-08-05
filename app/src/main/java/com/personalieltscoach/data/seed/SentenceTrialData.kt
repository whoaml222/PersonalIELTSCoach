package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.SentenceCardEntity

object SentenceTrialData {
    fun cards(now: Long): List<SentenceCardEntity> =
        sequenceOf(trialRowsA1, trialRowsA2, trialRowsB1)
            .flatMap { it.lineSequence() }
            .map(String::trim)
            .filter(String::isNotBlank)
            .map { row ->
                val parts = row.split('|')
                require(parts.size == 7) { "Invalid sentence trial row: $row" }
                SentenceCardEntity(
                    id = "trial-${parts[0]}",
                    level = parts[1],
                    category = parts[2],
                    sentence = parts[3],
                    translation = parts[4],
                    chunks = parts[5],
                    note = parts[6],
                    createdAt = now
                )
            }
            .toList()
}
