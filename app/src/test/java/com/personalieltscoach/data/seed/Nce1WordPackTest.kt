package com.personalieltscoach.data.seed

import org.junit.Assert.assertEquals
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
}
