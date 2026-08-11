package com.personalieltscoach.speech

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertEquals
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
class DictionarySpeechServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var cacheDirectory: File

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        cacheDirectory = File(context.cacheDir, "dictionary-test-${UUID.randomUUID()}")
    }

    @After
    fun tearDown() {
        server.shutdown()
        cacheDirectory.deleteRecursively()
    }

    @Test
    fun `downloaded dictionary clips are reused from cache`() = runTest {
        server.enqueue(audioResponse())
        server.enqueue(audioResponse())
        val service = service()

        val first = service.prepare("I wake up today.")
        val second = service.prepare("I wake up today.")

        assertEquals(2, first.size)
        assertEquals(first, second)
        assertEquals(2, server.requestCount)
        assertTrue(first.all { it.length() >= 512 })
        assertTrue(server.takeRequest().requestUrl?.queryParameter("type") == "1")
    }

    @Test
    fun `unsupported phrase automatically falls back to smaller clips`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        server.enqueue(audioResponse())
        server.enqueue(audioResponse())

        val files = service().prepare("check the pressure")

        assertEquals(2, files.size)
        assertEquals(4, server.requestCount)
        assertEquals("check the pressure", server.takeRequest().requestUrl?.queryParameter("audio"))
        assertEquals("check the pressure", server.takeRequest().requestUrl?.queryParameter("audio"))
        assertEquals("check", server.takeRequest().requestUrl?.queryParameter("audio"))
        assertEquals("the pressure", server.takeRequest().requestUrl?.queryParameter("audio"))
    }

    private fun service(): DictionarySpeechService {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return DictionarySpeechService(
            context = context,
            endpoint = server.url("/dictvoice"),
            cacheDirectory = cacheDirectory
        )
    }

    private fun audioResponse(): MockResponse = MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "audio/mpeg")
        .setBody(Buffer().write(ByteArray(1024) { 1 }))
}
