package com.personalieltscoach.speech

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionarySpeechSegmenterTest {
    @Test
    fun `whole sentences retain punctuation without word chunks`() {
        assertEquals(
            listOf("I wake up at six every morning."),
            DictionarySpeechSegmenter.split("I wake up at six every morning.")
        )
    }

    @Test
    fun `curly apostrophes are normalized for dictionary lookup`() {
        assertEquals(listOf("I'm ready."), DictionarySpeechSegmenter.split("I’m ready."))
    }

    @Test
    fun `phrases hyphens numbers and natural pauses are not discarded`() {
        assertEquals(listOf("Check the air-conditioning, please; set it to 20.5 degrees."),
            DictionarySpeechSegmenter.split("  Check the air-conditioning,\nplease;  set it to 20.5 degrees.  "))
    }

    @Test fun `single words still have a single clip`() {
        assertEquals(listOf("pressure"), DictionarySpeechSegmenter.split("pressure"))
    }

    @Test fun `empty or oversized input is rejected before a network request`() {
        listOf("", "你好。", "a".repeat(501)).forEach { input ->
            assertTrue(runCatching { DictionarySpeechSegmenter.split(input) }.exceptionOrNull() is IllegalArgumentException)
        }
        assertEquals(500, DictionarySpeechSegmenter.normalize("a".repeat(500)).length)
    }
}
