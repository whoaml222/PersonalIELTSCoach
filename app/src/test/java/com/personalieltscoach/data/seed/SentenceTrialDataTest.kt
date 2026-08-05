package com.personalieltscoach.data.seed

import com.personalieltscoach.domain.service.SentenceChunkCodec
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SentenceTrialDataTest {
    private val cards = SentenceTrialData.cards(now = 123L)

    @Test
    fun `trial pack contains exactly 300 unique cards`() {
        assertEquals(300, cards.size)
        assertEquals(300, cards.map { it.id }.distinct().size)
        assertEquals(300, cards.map { it.sentence.lowercase() }.distinct().size)
    }

    @Test
    fun `trial pack has 100 cards at each level`() {
        assertEquals(mapOf("A1" to 100, "A2" to 100, "B1" to 100), cards.groupingBy { it.level }.eachCount())
    }

    @Test
    fun `every card is fully covered by its phrase chunks`() {
        cards.forEach { card ->
            val chunks = SentenceChunkCodec.decode(card.chunks)
            assertTrue("No chunks for ${card.id}", chunks.isNotEmpty())
            assertTrue("Blank translation for ${card.id}", card.translation.isNotBlank())
            assertTrue("Blank note for ${card.id}", card.note.isNotBlank())
            assertEquals(
                "Chunks do not cover ${card.id}: ${card.sentence}",
                normalize(card.sentence),
                normalize(chunks.joinToString(" ") { it.english })
            )
        }
    }

    private fun normalize(value: String): String = value
        .lowercase()
        .replace(Regex("[^a-z0-9]"), "")
}
