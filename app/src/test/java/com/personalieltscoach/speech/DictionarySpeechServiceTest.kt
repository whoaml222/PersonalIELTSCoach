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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.SocketPolicy
import java.util.concurrent.TimeUnit

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
        val service = service()

        val first = service.prepare("I wake up today.")
        val second = service.prepare("I wake up today.")

        assertEquals(1, first.size)
        assertEquals(first, second)
        assertEquals(1, server.requestCount)
        assertTrue(first.all { it.length() >= 512 })
        val request = server.takeRequest()
        assertEquals("uk", request.requestUrl?.queryParameter("lan"))
        assertEquals("I wake up today.", request.requestUrl?.queryParameter("text"))
        assertEquals("/gettts", request.requestUrl?.encodedPath)
    }

    @Test
    fun `unsupported whole phrase fails explicitly without word sized fallback`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))
        val error = runCatching { service().prepare("check the pressure") }.exceptionOrNull()

        assertTrue(error is DictionarySpeechException)
        assertEquals(2, server.requestCount)
        assertEquals("check the pressure", server.takeRequest().requestUrl?.queryParameter("text"))
        assertEquals("check the pressure", server.takeRequest().requestUrl?.queryParameter("text"))
    }

    @Test fun `full sentence including the last word is requested once`() = runTest {
        server.enqueue(audioResponse())
        val text = "Could you check the air-conditioning before we leave, please?"
        val files = service().prepare(text)
        assertEquals(1, files.size)
        assertEquals(1, server.requestCount)
        assertEquals(text, server.takeRequest().requestUrl?.queryParameter("text"))
    }

    @Test fun `retry requests the whole utterance not separate words`() = runTest {
        server.enqueue(MockResponse().setResponseCode(503))
        server.enqueue(audioResponse())
        assertEquals(1, service().prepare("I'm ready, let's go.").size)
        repeat(2) { assertEquals("I'm ready, let's go.", server.takeRequest().requestUrl?.queryParameter("text")) }
        assertEquals(2, server.requestCount)
    }

    @Test fun `whole utterance cache cannot reuse legacy or different punctuation`() {
        val service = service()
        val oldKey = java.security.MessageDigest.getInstance("SHA-256")
            .digest("uk-dictionary-v1|i'm ready".toByteArray()).joinToString("") { "%02x".format(it) }
        assertTrue(oldKey != service.cacheKey("I'm ready"))
        assertTrue(service.cacheKey("Let's eat, Jack.") != service.cacheKey("Let's eat Jack."))
    }

    @Test fun `single word keeps UK dictionary and existing offline cache`() = runTest {
        server.enqueue(audioResponse())
        val service = service()
        val first = service.prepare("pressure")
        val request = server.takeRequest()
        assertEquals("/dictvoice", request.requestUrl?.encodedPath)
        assertEquals("pressure", request.requestUrl?.queryParameter("audio"))
        assertEquals("1", request.requestUrl?.queryParameter("type"))
        assertEquals(first, service.prepare("pressure"))
        assertEquals(1, server.requestCount)
        val oldKey = java.security.MessageDigest.getInstance("SHA-256")
            .digest("uk-dictionary-v1|pressure".toByteArray()).joinToString("") { "%02x".format(it) }
        assertEquals(oldKey, service.cacheKey("pressure"))
    }

    @Test fun `cancelling stops a pending network request without fallback downloads`() = runTest {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        val job = launch(Dispatchers.Default) { service().prepare("check the pressure") }
        assertTrue(server.takeRequest(5, TimeUnit.SECONDS) != null)
        // The socket callback runs on real threads; do not let runTest jump virtual time.
        withContext(Dispatchers.Default) { withTimeout(3000) { job.cancelAndJoin() } }
        assertEquals(1, server.requestCount)
    }

    private fun service(): DictionarySpeechService {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return DictionarySpeechService(
            context = context,
            endpoint = server.url("/dictvoice"),
            phraseEndpoint = server.url("/gettts"),
            cacheDirectory = cacheDirectory
        )
    }

    private fun audioResponse(): MockResponse = MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "audio/mpeg")
        .setBody(Buffer().write(ByteArray(1024) { 1 }))
}
