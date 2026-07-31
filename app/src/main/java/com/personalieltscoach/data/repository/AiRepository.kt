package com.personalieltscoach.data.repository

import com.personalieltscoach.ai.AIException
import com.personalieltscoach.ai.GPTProvider
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.MessageDigest

class AiRepository(
    private val database: CoachDatabase,
    private val settingsRepository: SettingsProvider,
    private val json: Json
) {
    private val apiCallMutex = Mutex()

    fun usage(date: String): Flow<Int> = database.aiDao().observeUsage(date)
    fun todayUsage(): Flow<Int> = usage(CoachRepository.today())

    suspend fun analyzeSentence(sentence: String): Pair<SentenceAnalysisResult, Boolean> {
        val normalized = sentence.trim()
        require(normalized.isNotBlank()) { "请输入英文句子" }
        val hash = hash("sentence:$normalized")
        database.aiDao().sentenceCache(hash)?.let { cached ->
            return cached.toModel() to true
        }
        return apiCallMutex.withLock {
            database.aiDao().sentenceCache(hash)?.let { cached ->
                return@withLock cached.toModel() to true
            }
            val settings = checkedSettings()
            val result = provider(settings).analyzeSentence(normalized)
            database.aiDao().saveSentenceCache(
                SentenceAnalysisCacheEntity(
                    sentenceHash = hash,
                    sentence = normalized,
                    translation = result.translation,
                    wordExplanation = json.encodeToString(result.wordExplanation),
                    phraseExplanation = json.encodeToString(result.phraseExplanation),
                    sentenceStructure = result.sentenceStructure,
                    grammarPoint = result.grammarPoint,
                    imitationExample = result.imitationExample,
                    imitationExampleTranslation = result.imitationExampleTranslation,
                    provider = settings.provider,
                    model = settings.model,
                    createdAt = System.currentTimeMillis()
                )
            )
            recordUsage(settings, "SENTENCE_ANALYSIS")
            result to false
        }
    }

    suspend fun correctWriting(
        promptChinese: String,
        text: String,
        level: String
    ): Pair<WritingCorrectionResult, Boolean> {
        val count = Regex("[A-Za-z]+(?:'[A-Za-z]+)?").findAll(text).count()
        if (count == 0) throw IllegalArgumentException("请先写一个英文句子")
        if (count > 300) throw IllegalArgumentException("第一版每次最多批改 300 个英文单词")
        val hash = hash("writing:$promptChinese|$text|$level")
        database.aiDao().responseCache(hash)?.let {
            return json.decodeFromString<WritingCorrectionResult>(it.responseJson) to true
        }
        return apiCallMutex.withLock {
            database.aiDao().responseCache(hash)?.let {
                return@withLock json.decodeFromString<WritingCorrectionResult>(it.responseJson) to true
            }
            val settings = checkedSettings()
            val result = provider(settings).correctWriting(promptChinese, text, level)
            database.aiDao().saveResponseCache(
                AiResponseCacheEntity(
                    requestHash = hash,
                    featureType = "WRITING_CORRECTION",
                    responseJson = json.encodeToString(result),
                    provider = settings.provider,
                    model = settings.model,
                    createdAt = System.currentTimeMillis()
                )
            )
            database.writingDao().insert(
                WritingRecordEntity(
                    promptChinese = promptChinese,
                    userText = text,
                    correctedText = result.correctedText,
                    explanation = json.encodeToString(result),
                    level = level,
                    createdAt = System.currentTimeMillis()
                )
            )
            recordUsage(settings, "WRITING_CORRECTION")
            result to false
        }
    }

    suspend fun dailySuggestion(progress: UserProgressSummary): DailySuggestionResult {
        val hash = hash("daily:${CoachRepository.today()}:${json.encodeToString(progress)}")
        database.aiDao().responseCache(hash)?.let {
            return json.decodeFromString(it.responseJson)
        }
        return apiCallMutex.withLock {
            database.aiDao().responseCache(hash)?.let {
                return@withLock json.decodeFromString<DailySuggestionResult>(it.responseJson)
            }
            val settings = checkedSettings()
            val result = provider(settings).generateDailySuggestion(progress)
            database.aiDao().saveResponseCache(
                AiResponseCacheEntity(
                    requestHash = hash,
                    featureType = "DAILY_SUGGESTION",
                    responseJson = json.encodeToString(result),
                    provider = settings.provider,
                    model = settings.model,
                    createdAt = System.currentTimeMillis()
                )
            )
            recordUsage(settings, "DAILY_SUGGESTION")
            result
        }
    }

    suspend fun testConnection(): Boolean {
        val settings = settingsRepository.current()
        if (settings.apiKey.isBlank()) throw AIException("请先填写 API Key")
        return provider(settings).testConnection()
    }

    suspend fun clearCache() {
        database.aiDao().clearSentenceCache()
        database.aiDao().clearResponseCache()
    }

    private suspend fun checkedSettings(): CoachSettings {
        val settings = settingsRepository.current()
        if (settings.apiKey.isBlank()) throw AIException("请先到设置页填写 API Key")
        val used = database.aiDao().usage(CoachRepository.today())
        if (used >= settings.dailyAiLimit) {
            throw AIException("今日 AI 调用已达到上限（${settings.dailyAiLimit} 次）")
        }
        return settings
    }

    private fun provider(settings: CoachSettings) = GPTProvider(
        apiKey = settings.apiKey,
        model = settings.model,
        json = json
    )

    private suspend fun recordUsage(settings: CoachSettings, feature: String) {
        val date = CoachRepository.today()
        val old = database.aiDao().usageRecord(date, settings.provider, settings.model, feature)
        database.aiDao().upsertUsage(
            old?.copy(callCount = old.callCount + 1)
                ?: ApiUsageRecordEntity(
                    date = date,
                    provider = settings.provider,
                    model = settings.model,
                    featureType = feature,
                    callCount = 1,
                    createdAt = System.currentTimeMillis()
                )
        )
    }

    private fun SentenceAnalysisCacheEntity.toModel() = SentenceAnalysisResult(
        translation = translation,
        wordExplanation = json.decodeFromString(wordExplanation),
        phraseExplanation = json.decodeFromString(phraseExplanation),
        sentenceStructure = sentenceStructure,
        grammarPoint = grammarPoint,
        imitationExample = imitationExample,
        imitationExampleTranslation = imitationExampleTranslation
    )

    private fun hash(value: String): String = MessageDigest.getInstance("SHA-256")
        .digest(value.toByteArray())
        .joinToString("") { "%02x".format(it) }
}
