package com.personalieltscoach.data.repository

import org.junit.Assert.*
import org.junit.Test

class SettingsPrivacyTest {
    @Test fun diagnosticRepresentationNeverContainsCredential() {
        val settings = CoachSettings(apiKey = "private-test-credential")
        assertFalse(settings.toString().contains("private-test-credential"))
        assertTrue(settings.toString().contains("redacted"))
    }
}
