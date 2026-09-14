package com.personalieltscoach.domain.service

import org.junit.Assert.assertEquals
import org.junit.Test

class TextSegmenterTest {
    @Test fun keepsTitlesInitialsAndDecimalsIntact() {
        assertEquals(listOf("Mr. Brown paid £3.50.", "Dr. Smith is here."),
            TextSegmenter.sentences("Mr. Brown paid £3.50. Dr. Smith is here."))
        assertEquals(listOf("J. Smith works in the U.S. office."),
            TextSegmenter.sentences("J. Smith works in the U.S. office."))
    }
    @Test fun handlesLineWrappingParagraphsAndQuotationMarks() {
        assertEquals(listOf("I work near the station.", "\"Are you ready?\"", "Yes!"),
            TextSegmenter.sentences("I work near\nthe station.\n\n\"Are you ready?\" Yes!"))
        assertEquals(emptyList<String>(), TextSegmenter.sentences("  \n  "))
    }
}
