package com.personalieltscoach.speech

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class DictionarySpeechService(
    context: Context,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build(),
    private val endpoint: HttpUrl = ENDPOINT.toHttpUrl(),
    private val cacheDirectory: File = File(context.cacheDir, CACHE_DIRECTORY)
) {
    private val downloadMutex = Mutex()

    suspend fun prepare(text: String): List<File> = withContext(Dispatchers.IO) {
        val segments = DictionarySpeechSegmenter.split(text)
        require(segments.isNotEmpty()) { "没有可朗读的英文" }
        cacheDirectory.mkdirs()
        trimCache()
        segments.flatMap { prepareWithFallback(it) }
    }

    fun invalidate(files: Collection<File>) {
        files.forEach { file ->
            if (file.parentFile == cacheDirectory) runCatching { file.delete() }
        }
    }

    private suspend fun prepareWithFallback(segment: String): List<File> {
        return runCatching { listOf(prepareSegment(segment)) }
            .getOrElse { originalError ->
                if (originalError is CancellationException) throw originalError
                // Splitting cannot repair an offline network or a timed-out socket.
                if (originalError !is DictionarySpeechException) throw originalError
                val halves = DictionarySpeechSegmenter.halve(segment)
                if (halves == null) throw originalError
                prepareWithFallback(halves.first) + prepareWithFallback(halves.second)
            }
    }

    private suspend fun prepareSegment(segment: String): File {
        val target = cacheFile(segment)
        if (target.isUsableAudio()) {
            target.setLastModified(System.currentTimeMillis())
            return target
        }
        return downloadMutex.withLock {
            if (target.isUsableAudio()) return@withLock target
            var lastError: Throwable? = null
            repeat(2) {
                currentCoroutineContext().ensureActive()
                runCatching { download(segment, target) }
                    .onSuccess { return@withLock it }
                    .onFailure { error -> lastError = error }
            }
            throw lastError ?: DictionarySpeechException("英音词典暂时不可用")
        }
    }

    private suspend fun download(segment: String, target: File): File {
        val url = endpoint.newBuilder()
            .addQueryParameter("audio", segment)
            .addQueryParameter("type", "1")
            .build()
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .header("Referer", "https://dict.youdao.com/")
            .get()
            .build()
        val temporary = File.createTempFile("dictionary-", ".tmp", cacheDirectory)
        try {
            val audio = fetchAudio(request)
            currentCoroutineContext().ensureActive()
            temporary.writeBytes(audio)
            currentCoroutineContext().ensureActive()
            if (!temporary.isUsableAudio()) {
                throw DictionarySpeechException("词典返回的音频无效")
            }
            if (!temporary.renameTo(target)) {
                temporary.copyTo(target, overwrite = true)
            }
            target.setLastModified(System.currentTimeMillis())
            return target
        } finally {
            if (temporary.exists()) temporary.delete()
        }
    }

    private suspend fun fetchAudio(request: Request): ByteArray = suspendCancellableCoroutine { continuation ->
        val call = client.newCall(request)
        continuation.invokeOnCancellation { call.cancel() }
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, error: IOException) {
                if (continuation.isActive) continuation.resumeWithException(error)
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    val bytes = response.use {
                        val body = it.body
                        if (!it.isSuccessful || body == null || body.contentType()?.type != "audio")
                            throw DictionarySpeechException("词典中没有找到这段英音")
                        require(body.contentLength() <= 4L * 1024 * 1024) { "词典音频异常过大" }
                        body.byteStream().use { input ->
                            ByteArrayOutputStream().use { output ->
                                val buffer = ByteArray(8192)
                                while (true) {
                                    if (!continuation.isActive) throw CancellationException()
                                    val count = input.read(buffer)
                                    if (count < 0) break
                                    require(output.size() + count <= 4 * 1024 * 1024) { "词典音频异常过大" }
                                    output.write(buffer, 0, count)
                                }
                                output.toByteArray()
                            }
                        }
                    }
                    if (continuation.isActive) continuation.resume(bytes)
                } catch (error: Exception) {
                    if (continuation.isActive) continuation.resumeWithException(error)
                }
            }
        })
    }

    private fun cacheFile(segment: String): File = File(cacheDirectory, "${cacheKey(segment)}.mp3")

    internal fun cacheKey(segment: String): String = MessageDigest.getInstance("SHA-256")
        .digest("uk-dictionary-v1|${segment.lowercase().trim()}".toByteArray())
        .joinToString("") { "%02x".format(it) }

    private fun trimCache() {
        val files = cacheDirectory.listFiles().orEmpty().filter(File::isFile)
        var total = files.sumOf(File::length)
        if (total <= MAX_CACHE_BYTES) return
        files.sortedBy(File::lastModified).forEach { file ->
            if (total <= TARGET_CACHE_BYTES) return
            val size = file.length()
            if (file.delete()) total -= size
        }
    }

    private fun File.isUsableAudio(): Boolean = isFile && length() >= MIN_AUDIO_BYTES

    private companion object {
        const val ENDPOINT = "https://dict.youdao.com/dictvoice"
        const val CACHE_DIRECTORY = "uk_dictionary_speech"
        const val USER_AGENT = "PersonalIELTSCoach/1.6.3"
        const val MIN_AUDIO_BYTES = 512L
        const val MAX_CACHE_BYTES = 64L * 1024L * 1024L
        const val TARGET_CACHE_BYTES = 48L * 1024L * 1024L
    }
}

object DictionarySpeechSegmenter {
    private val TOKEN = Regex("[A-Za-zÀ-ÖØ-öø-ÿ0-9]+(?:['’][A-Za-zÀ-ÖØ-öø-ÿ]+)*")

    fun split(text: String): List<String> = TOKEN.findAll(text)
        .map { it.value.replace('’', '\'') }
        .toList()
        .chunked(3)
        .map { it.joinToString(" ") }

    fun halve(segment: String): Pair<String, String>? {
        val words = segment.split(' ').filter(String::isNotBlank)
        if (words.size < 2) return null
        val middle = words.size / 2
        return words.take(middle).joinToString(" ") to words.drop(middle).joinToString(" ")
    }
}

class DictionarySpeechException(message: String) : Exception(message)
