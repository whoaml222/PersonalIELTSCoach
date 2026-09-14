package com.personalieltscoach.data.repository

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = Application::class)
class SecureStorageFailureTest {
    @Test fun unavailableKeystoreDoesNotBlockLearningOrWritePlaintextCredentials() = runTest {
        // The desktop JVM has no AndroidKeyStore. Exercise the production fail-closed path.
        val context = ApplicationProvider.getApplicationContext<Application>()
        val settings = SettingsRepository(context)
        assertNotNull(settings.secureStorageError.value)
        assertEquals("", settings.current().apiKey)
        settings.setSpeechRate(.8f)
        assertEquals(.8f, settings.current().speechRate)
        try { settings.saveApiKey("not-a-real-credential"); fail("Insecure credential storage allowed") }
        catch (_: IllegalStateException) { }
        assertEquals("", settings.current().apiKey)
        val prefs = java.io.File(context.applicationInfo.dataDir, "shared_prefs")
        assertFalse(prefs.walkTopDown().filter { it.isFile }.any { it.readText().contains("not-a-real-credential") })
    }
}
