package com.personalieltscoach.data.seed

import org.junit.Assert.*
import org.junit.Test

class ReviewedMeaningsTest {
    @Test fun commonSourceLabelErrorsStayCorrectedInBothPacks() {
        val words = Nce1WordPack.words(0) + Paul1000SentencePack.words(0)
        val expected = mapOf("too" to "adv.", "invite" to "v.", "fail" to "v.",
            "subway" to "n.", "other" to "det.", "he" to "pron.", "salt" to "n.")
        expected.forEach { (word, prefix) ->
            val matches = words.filter { it.word.equals(word, true) }
            assertTrue(matches.isNotEmpty())
            assertTrue("$word must retain the corrected label", matches.all { it.meaning.startsWith(prefix) })
        }
        assertTrue(words.first { it.word.equals("an", true) }.meaning.contains("读音"))
    }
}
