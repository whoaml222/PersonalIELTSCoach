package com.personalieltscoach.ai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.repository.CoachSettings
import com.personalieltscoach.data.repository.SettingsProvider
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class OpenAiSpeechServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var cacheDirectory: File
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        cacheDirectory = File(context.cacheDir, "speech-test-${UUID.randomUUID()}")
    }

    @After
    fun tearDown() {
        server.shutdown()
        cacheDirectory.deleteRecursively()
    }

    @Test
    fun generatesMarinBritishSpeechAndCachesIt() = runTest {
        val audioBytes = "fake-mp3-audio".toByteArray()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "audio/mpeg")
                .setBody(Buffer().write(audioBytes))
        )
        val service = service(apiKey = "test-key")

        val generated = service.synthesize("  Welcome   to your IELTS coach. ")
        val cached = service.synthesize("Welcome to your IELTS coach.")

        assertFalse(generated.fromCache)
        assertTrue(cached.fromCache)
        assertArrayEquals(audioBytes, generated.file.readBytes())
        assertEquals(generated.file, cached.file)
        assertEquals(1, server.requestCount)

        val request = server.takeRequest()
        assertEquals("Bearer test-key", request.getHeader("Authorization"))
        val body = json.parseToJsonElement(request.body.readUtf8()).jsonObject
        assertEquals(OpenAiSpeechService.MODEL, body.getValue("model").jsonPrimitive.content)
        assertEquals(OpenAiSpeechService.VOICE, body.getValue("voice").jsonPrimitive.content)
        assertEquals(
            "Welcome to your IELTS coach.",
            body.getValue("input").jsonPrimitive.content
        )
        assertEquals(
            OpenAiSpeechService.BRITISH_ENGLISH_INSTRUCTIONS,
            body.getValue("instructions").jsonPrimitive.content
        )
    }

    @Test
    fun missingApiKeyDoesNotCallOpenAi() = runTest {
        val error = runCatching { service(apiKey = "").synthesize("Hello") }.exceptionOrNull()

        assertTrue(error is SpeechException)
        assertTrue(error?.message.orEmpty().contains("API Key"))
        assertEquals(0, server.requestCount)
    }

    private fun service(apiKey: String): OpenAiSpeechService {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return OpenAiSpeechService(
            context = context,
            settingsProvider = object : SettingsProvider {
                override suspend fun current() = CoachSettings(apiKey = apiKey)
            },
            json = json,
            client = OkHttpClient(),
            endpoint = server.url("/v1/audio/speech"),
            cacheDirectory = cacheDirectory
        )
    }
}
