package com.personalieltscoach.ai

import com.personalieltscoach.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class GPTProvider(
    private val apiKey: String,
    private val model: String,
    private val json: Json,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) : AIService {

    override suspend fun analyzeSentence(sentence: String): SentenceAnalysisResult {
        val prompt = """
            Analyze this English sentence for a beginner Chinese learner.
            Sentence: $sentence

            Explain in simple Chinese. Identify basic subject, predicate, object or complement.
            Give exactly one useful imitation sentence.
        """.trimIndent()
        return json.decodeFromString(callStructured("sentence_analysis", sentenceSchema, prompt))
    }

    override suspend fun translateSentence(sentence: String): TranslationResult =
        json.decodeFromString(
            callStructured(
                "translation",
                objectSchema("translation"),
                "Translate this English sentence naturally into simplified Chinese: $sentence"
            )
        )

    override suspend fun explainGrammar(sentence: String): GrammarExplanationResult =
        json.decodeFromString(
            callStructured(
                "grammar_explanation",
                objectSchema("sentenceStructure", "grammarPoint"),
                "Explain the basic structure and grammar of this sentence in simple Chinese: $sentence"
            )
        )

    override suspend fun correctWriting(
        promptChinese: String,
        text: String,
        userLevel: String
    ): WritingCorrectionResult {
        val prompt = """
            Correct this English writing from a beginner Chinese learner.
            Chinese prompt: $promptChinese
            User writing: $text
            User level: $userLevel

            Explain each mistake in simple Chinese. Prefer a correct simple sentence over an
            unnecessarily advanced rewrite.
        """.trimIndent()
        return json.decodeFromString(callStructured("writing_correction", writingSchema, prompt))
    }

    override suspend fun generateDailySuggestion(progress: UserProgressSummary): DailySuggestionResult =
        json.decodeFromString(
            callStructured(
                "daily_suggestion",
                objectSchema("suggestion"),
                "Give one concise Chinese learning suggestion for tomorrow based on: ${json.encodeToString(progress)}"
            )
        )

    override suspend fun testConnection(): Boolean {
        val result = callStructured(
            "connection_test",
            objectSchema("status"),
            "Return status as exactly OK."
        )
        return Json.parseToJsonElement(result).jsonObject["status"]?.jsonPrimitive?.content == "OK"
    }

    private suspend fun callStructured(
        schemaName: String,
        schema: JsonObject,
        prompt: String
    ): String = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) throw AIException("请先在设置页填写 API Key")
        val body = buildJsonObject {
            put("model", model)
            put("store", false)
            put("instructions", "You are a patient English coach for a Chinese beginner.")
            put("input", prompt)
            putJsonObject("text") {
                putJsonObject("format") {
                    put("type", "json_schema")
                    put("name", schemaName)
                    put("strict", true)
                    put("schema", schema)
                }
            }
        }.toString()
        val request = Request.Builder()
            .url("https://api.openai.com/v1/responses")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(body.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string().orEmpty()
            if (!response.isSuccessful) {
                val message = runCatching {
                    Json.parseToJsonElement(responseBody).jsonObject["error"]
                        ?.jsonObject?.get("message")?.jsonPrimitive?.content
                }.getOrNull()
                throw AIException(message ?: "AI 请求失败：HTTP ${response.code}")
            }
            extractOutputText(responseBody)
        }
    }

    private fun extractOutputText(body: String): String {
        val root = Json.parseToJsonElement(body).jsonObject
        root["output_text"]?.jsonPrimitive?.contentOrNull?.let { return it }
        root["output"]?.jsonArray?.forEach { output ->
            output.jsonObject["content"]?.jsonArray?.forEach { content ->
                content.jsonObject["text"]?.jsonPrimitive?.contentOrNull?.let { return it }
            }
        }
        throw IOException("AI 返回内容为空，请稍后重试")
    }

    private fun objectSchema(vararg fields: String): JsonObject = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {
            fields.forEach { field ->
                putJsonObject(field) { put("type", "string") }
            }
        }
        putJsonArray("required") { fields.forEach(::add) }
        put("additionalProperties", false)
    }

    private val sentenceSchema = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {
            putJsonObject("translation") { put("type", "string") }
            putJsonObject("wordExplanation") {
                put("type", "array")
                putJsonObject("items") {
                    put("type", "object")
                    putJsonObject("properties") {
                        listOf("word", "meaning", "role").forEach {
                            putJsonObject(it) { put("type", "string") }
                        }
                    }
                    putJsonArray("required") { listOf("word", "meaning", "role").forEach(::add) }
                    put("additionalProperties", false)
                }
            }
            putJsonObject("phraseExplanation") {
                put("type", "array")
                putJsonObject("items") {
                    put("type", "object")
                    putJsonObject("properties") {
                        putJsonObject("phrase") { put("type", "string") }
                        putJsonObject("meaning") { put("type", "string") }
                    }
                    putJsonArray("required") { add("phrase"); add("meaning") }
                    put("additionalProperties", false)
                }
            }
            listOf(
                "sentenceStructure", "grammarPoint", "imitationExample",
                "imitationExampleTranslation"
            ).forEach { putJsonObject(it) { put("type", "string") } }
        }
        putJsonArray("required") {
            listOf(
                "translation", "wordExplanation", "phraseExplanation", "sentenceStructure",
                "grammarPoint", "imitationExample", "imitationExampleTranslation"
            ).forEach(::add)
        }
        put("additionalProperties", false)
    }

    private val writingSchema = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {
            listOf(
                "correctedText", "chineseTranslation", "betterExpression",
                "nextPracticeSuggestion"
            ).forEach { putJsonObject(it) { put("type", "string") } }
            putJsonObject("mistakes") {
                put("type", "array")
                putJsonObject("items") {
                    put("type", "object")
                    putJsonObject("properties") {
                        listOf("original", "corrected", "reason").forEach {
                            putJsonObject(it) { put("type", "string") }
                        }
                    }
                    putJsonArray("required") { listOf("original", "corrected", "reason").forEach(::add) }
                    put("additionalProperties", false)
                }
            }
        }
        putJsonArray("required") {
            listOf(
                "correctedText", "chineseTranslation", "mistakes",
                "betterExpression", "nextPracticeSuggestion"
            ).forEach(::add)
        }
        put("additionalProperties", false)
    }
}

class AIException(message: String) : Exception(message)
