package com.personalieltscoach.data.seed

import com.personalieltscoach.domain.service.SentenceChunkCodec
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SentenceTrialDataTest {
    private val cards = SentenceTrialData.cards(now = 123L)
    private val words = Paul1000SentencePack.words(now = 123L)

    @Test
    fun `Paul pack contains every source word exactly once`() {
        assertEquals(Paul1000WordPack.SOURCE_ENTRY_COUNT, cards.size)
        assertEquals(Paul1000WordPack.SOURCE_ENTRY_COUNT, words.size)
        assertEquals(cards.size, cards.map { it.id }.distinct().size)
        assertEquals(words.size, words.map { it.word.lowercase() }.distinct().size)
        assertEquals(cards.size, cards.map { it.sentence.lowercase() }.distinct().size)
    }

    @Test
    fun `every sentence contains its focus word and complete learning content`() {
        val missingFocusWords = mutableListOf<String>()
        cards.forEach { card ->
            val focusWord = requireNotNull(Paul1000SentencePack.focusWord(card.id))
            val chunks = SentenceChunkCodec.decode(card.chunks)
            if (!containsWord(card.sentence, focusWord)) {
                missingFocusWords += "$focusWord -> ${card.sentence}"
            }
            assertEquals("Expected focus and sentence chunks for ${card.id}", 2, chunks.size)
            assertEquals(focusWord, chunks.first().english)
            assertEquals(card.sentence, chunks.last().english)
            assertTrue("Blank translation for ${card.id}", card.translation.isNotBlank())
            assertTrue("Blank note for ${card.id}", card.note.isNotBlank())
            assertTrue("Blank category for ${card.id}", card.category.isNotBlank())
        }
        assertTrue("Sentences missing focus words:\n${missingFocusWords.joinToString("\n")}", missingFocusWords.isEmpty())
    }

    @Test
    fun `examples are short spoken sentences without metalinguistic placeholders`() {
        val banned = listOf(
            "please remember the word",
            "i hear ",
            "everyday english",
            "the first item"
        )
        val invalid = cards.mapNotNull { card ->
            val normalized = card.sentence.lowercase()
            when {
                Regex("[A-Za-z]+(?:'[A-Za-z]+)?").findAll(card.sentence).count() > 14 ->
                    "too long: ${card.sentence}"
                banned.any(normalized::contains) -> "placeholder: ${card.sentence}"
                else -> null
            }
        }
        assertTrue("Invalid fragment examples:\n${invalid.joinToString("\n")}", invalid.isEmpty())

        val structureCounts = cards.map { card ->
            val focusWord = requireNotNull(Paul1000SentencePack.focusWord(card.id))
            Regex("(?i)(?<![A-Za-z])${Regex.escape(focusWord)}(?![A-Za-z])")
                .replace(card.sentence.lowercase(), "{word}")
        }.groupingBy { it }.eachCount()
        assertTrue(
            "One sentence structure is overused: ${structureCounts.maxBy { it.value }}",
            structureCounts.values.max() <= 30
        )
    }

    @Test
    fun `all bundled words have British dictionary phonetics`() {
        words.forEach { word ->
            assertTrue("Missing phonetic for ${word.word}", word.phonetic.isNotBlank())
            assertTrue(
                "Phonetic is not wrapped for ${word.word}",
                word.phonetic.startsWith('/') && word.phonetic.endsWith('/')
            )
        }
    }

    private fun containsWord(sentence: String, word: String): Boolean =
        Regex("(?i)(?<![A-Za-z])${Regex.escape(word)}(?![A-Za-z])").containsMatchIn(sentence)
}
