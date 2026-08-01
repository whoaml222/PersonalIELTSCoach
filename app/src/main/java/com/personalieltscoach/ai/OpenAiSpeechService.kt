package com.personalieltscoach.ai

import android.content.Context
import com.personalieltscoach.data.repository.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

data class SpeechAudio(
    val file: File,
    val fromCache: Boolean
)

class OpenAiSpeechService(
    context: Context,
    private val settingsProvider: SettingsProvider,
    private val json: Json,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .build(),
    private val endpoint: HttpUrl = SPEECH_ENDPOINT.toHttpUrl(),
    private val cacheDirectory: File = File(context.cacheDir, CACHE_DIRECTORY)
) {
    private val generationMutex = Mutex()

    suspend fun synthesize(text: String): SpeechAudio = withContext(Dispatchers.IO) {
        val normalized = normalize(text)
        require(normalized.isNotBlank()) { "请输入需要朗读的英文" }
        require(normalized.length <= MAX_INPUT_CHARACTERS) {
            "单次朗读最多支持 $MAX_INPUT_CHARACTERS 个字符"
        }

        val output = cacheFile(normalized)
        if (output.isUsableAudio()) return@withContext SpeechAudio(output, fromCache = true)

        generationMutex.withLock {
            if (output.isUsableAudio()) return@withLock SpeechAudio(output, fromCache = true)

            val apiKey = settingsProvider.current().apiKey
            if (apiKey.isBlank()) {
                throw SpeechException("请先在设置页填写并保存 OpenAI API Key")
            }
            cacheDirectory.mkdirs()
            val temporary = File.createTempFile("marin-", ".tmp", cacheDirectory)
            try {
                val body = buildJsonObject {
                    put("model", MODEL)
                    put("voice", VOICE)
                    put("input", normalized)
                    put("instructions", BRITISH_ENGLISH_INSTRUCTIONS)
                    put("response_format", "mp3")
                }.toString()
                val request = Request.Builder()
                    .url(endpoint)
                    .header("Authorization", "Bearer $apiKey")
                    .header("Content-Type", "application/json")
                    .post(body.toRequestBody(JSON_MEDIA_TYPE))
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        val responseText = response.body?.string().orEmpty()
                        val message = runCatching {
                            json.parseToJsonElement(responseText).jsonObject["error"]
                                ?.jsonObject?.get("message")?.jsonPrimitive?.content
                        }.getOrNull()
                        throw SpeechException(message ?: "Marin 语音生成失败：HTTP ${response.code}")
                    }
                    val responseBody = response.body
                        ?: throw SpeechException("Marin 语音返回内容为空")
                    responseBody.byteStream().use { input ->
                        temporary.outputStream().use(input::copyTo)
                    }
                }
                if (!temporary.isUsableAudio()) throw SpeechException("Marin 语音文件生成失败")
                if (!temporary.renameTo(output)) {
                    temporary.copyTo(output, overwrite = true)
                    temporary.delete()
                }
                SpeechAudio(output, fromCache = false)
            } finally {
                if (temporary.exists()) temporary.delete()
            }
        }
    }

    suspend fun clearCache() = withContext(Dispatchers.IO) {
        cacheDirectory.listFiles().orEmpty().forEach(File::delete)
    }

    internal fun cacheKey(text: String): String {
        val source = "$MODEL|$VOICE|$BRITISH_ENGLISH_INSTRUCTIONS|${normalize(text)}"
        return MessageDigest.getInstance("SHA-256")
            .digest(source.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }

    private fun cacheFile(text: String): File = File(cacheDirectory, "${cacheKey(text)}.mp3")

    private fun normalize(text: String): String = text.trim().replace(WHITESPACE, " ")

    private fun File.isUsableAudio(): Boolean = isFile && length() > 0L

    companion object {
        const val MODEL = "gpt-4o-mini-tts"
        const val VOICE = "marin"
        const val MAX_INPUT_CHARACTERS = 4096
        const val BRITISH_ENGLISH_INSTRUCTIONS =
            "Speak in clear, natural Standard British English, like an experienced IELTS examiner. " +
                "Use precise pronunciation, calm pacing, natural intonation, and brief pauses " +
                "between clauses. Do not use an American accent."

        private const val SPEECH_ENDPOINT = "https://api.openai.com/v1/audio/speech"
        private const val CACHE_DIRECTORY = "openai_marin_speech"
        private val JSON_MEDIA_TYPE = "application/json".toMediaType()
        private val WHITESPACE = Regex("\\s+")
    }
}

class SpeechException(message: String) : Exception(message)
