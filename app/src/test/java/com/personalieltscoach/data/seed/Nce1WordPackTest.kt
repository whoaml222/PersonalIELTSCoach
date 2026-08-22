package com.personalieltscoach.data.seed

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Nce1WordPackTest {
    @Test
    fun packMatchesValidatedSourceCountsAndContainsNoDuplicateCards() {
        val words = Nce1WordPack.words(now = 123L)

        assertEquals(1_108, Nce1WordPack.SOURCE_ENTRY_COUNT)
        assertEquals(1_021, Nce1WordPack.UNIQUE_WORD_COUNT)
        assertEquals(Nce1WordPack.UNIQUE_WORD_COUNT, words.size)
        assertEquals(words.size, words.map { it.word.lowercase() }.distinct().size)
        assertTrue(words.all { it.word.isNotBlank() })
        assertTrue(words.all { it.meaning.isNotBlank() })
        assertTrue(words.all { it.phonetic.startsWith("/") && it.phonetic.endsWith("/") })
        assertTrue(words.all { it.level.startsWith("NCE1 Lesson ") })
        assertTrue(words.any { it.word.equals("excuse", ignoreCase = true) })
        assertTrue(words.any { it.word.equals("prosecute", ignoreCase = true) })
        assertTrue(
            words.first { it.word.equals("work", ignoreCase = true) }
                .example != "Please remember the word \"work\"."
        )
    }

    @Test
    fun everyWordHasAContextualExampleWithVariedSentenceStructures() {
        val words = Nce1WordPack.words(now = 123L)

        assertFalse(words.any { it.example.startsWith("Please remember the word") })
        assertTrue(words.all { it.example.isNotBlank() && it.exampleTranslation.isNotBlank() })
        assertTrue(words.all { containsTerm(it.example, it.word) })

        val sweden = words.first { it.word.equals("Sweden", ignoreCase = true) }
        assertTrue(sweden.example.contains("Sweden"))
        assertTrue(sweden.exampleTranslation.contains("瑞典"))
        assertFalse(sweden.example.contains("remember the word", ignoreCase = true))

        val structureCounts = words
            .map { word ->
                Regex(
                    pattern = "(?<![A-Za-z])${Regex.escape(word.word)}(?![A-Za-z])",
                    option = RegexOption.IGNORE_CASE
                ).replace(word.example.lowercase(), "{word}")
            }
            .groupingBy { it }
            .eachCount()

        assertTrue("One example structure is overused: ${structureCounts.maxBy { it.value }}", structureCounts.values.max() <= 25)
        assertTrue(words.map { it.example }.distinct().size >= 700)
    }

    private fun containsTerm(sentence: String, term: String): Boolean =
        Regex(
            pattern = "(?<![A-Za-z])${Regex.escape(term)}(?![A-Za-z])",
            option = RegexOption.IGNORE_CASE
        ).containsMatchIn(sentence)
}
