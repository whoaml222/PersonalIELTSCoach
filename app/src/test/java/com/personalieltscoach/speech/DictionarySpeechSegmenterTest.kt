package com.personalieltscoach.speech

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DictionarySpeechSegmenterTest {
    @Test
    fun `long sentences are split into short dictionary phrases`() {
        assertEquals(
            listOf("I wake up", "at six every", "morning"),
            DictionarySpeechSegmenter.split("I wake up at six every morning.")
        )
    }

    @Test
    fun `curly apostrophes are normalized for dictionary lookup`() {
        assertEquals(listOf("I'm ready"), DictionarySpeechSegmenter.split("I’m ready."))
    }

    @Test
    fun `failed phrases can be reduced to smaller lookups`() {
        assertEquals("check" to "the pressure", DictionarySpeechSegmenter.halve("check the pressure"))
        assertNull(DictionarySpeechSegmenter.halve("pressure"))
    }
}
