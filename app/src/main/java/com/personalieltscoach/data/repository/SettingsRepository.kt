package com.personalieltscoach.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.personalieltscoach.BuildConfig
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val Context.dataStore by preferencesDataStore("coach_settings")

data class CoachSettings(
    val provider: String = "GPT",
    val model: String = "gpt-5.4-mini",
    val dailyAiLimit: Int = 20,
    val dailyNewWords: Int = 10,
    val dailyReviewWords: Int = 20,
    val dailySentences: Int = 5,
    val targetScore: Float = 7.0f,
    val updateRepository: String = BuildConfig.DEFAULT_UPDATE_REPOSITORY,
    val autoCheckUpdates: Boolean = true,
    val lastUpdateCheckAt: Long = 0,
    val speechMode: String = "AUTO",
    val speechRate: Float = 0.92f,
    val apiKey: String = ""
) {
    // Never include credentials in crash reports or incidental diagnostic logging.
    override fun toString(): String = "CoachSettings(provider=$provider, model=$model, apiKey=[redacted])"
}

interface SettingsProvider {
    suspend fun current(): CoachSettings
}

class SettingsRepository(private val context: Context) : SettingsProvider {
    private val _secureStorageError = MutableStateFlow<String?>(null)
    val secureStorageError = _secureStorageError.asStateFlow()
    private val encryptedPreferences = try {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(context, "secure_coach_settings", masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    } catch (_: Exception) {
        // Fail closed for credentials, not for the user's offline learning data.
        // Never replace damaged ciphertext with plaintext or silently reset the key store.
        _secureStorageError.value = STORAGE_UNAVAILABLE
        null
    }
    private val apiKey = MutableStateFlow(try {
        encryptedPreferences?.getString(KEY_API, "").orEmpty()
    } catch (_: Exception) {
        _secureStorageError.value = STORAGE_UNAVAILABLE
        ""
    })

    val settings: Flow<CoachSettings> = combine(context.dataStore.data.catch { error ->
        if (error is java.io.IOException) emit(emptyPreferences()) else throw error
    }, apiKey) { prefs, key ->
        CoachSettings(
            provider = prefs[PROVIDER] ?: "GPT",
            model = prefs[MODEL] ?: "gpt-5.4-mini",
            dailyAiLimit = prefs[AI_LIMIT] ?: 20,
            dailyNewWords = prefs[NEW_WORDS] ?: 10,
            dailyReviewWords = prefs[REVIEW_WORDS] ?: 20,
            dailySentences = prefs[SENTENCES] ?: 5,
            targetScore = prefs[TARGET_SCORE] ?: 7.0f,
            updateRepository = prefs[UPDATE_REPOSITORY] ?: BuildConfig.DEFAULT_UPDATE_REPOSITORY,
            autoCheckUpdates = prefs[AUTO_CHECK_UPDATES] ?: true,
            lastUpdateCheckAt = prefs[LAST_UPDATE_CHECK_AT] ?: 0,
            speechMode = prefs[SPEECH_MODE] ?: "AUTO",
            speechRate = prefs[SPEECH_RATE] ?: 0.92f,
            apiKey = key
        )
    }.distinctUntilChanged()

    override suspend fun current(): CoachSettings = settings.first()

    suspend fun saveApiKey(value: String) = withContext(Dispatchers.IO) {
        val preferences = encryptedPreferences
        check(preferences != null && _secureStorageError.value == null) { STORAGE_UNAVAILABLE }
        try {
            check(preferences.edit().putString(KEY_API, value.trim()).commit()) { "密钥未能保存，请检查存储空间。" }
            apiKey.value = value.trim()
        } catch (_: Exception) {
            _secureStorageError.value = STORAGE_UNAVAILABLE
            apiKey.value = ""
            throw IllegalStateException(STORAGE_UNAVAILABLE)
        }
    }

    suspend fun setModel(value: String) = context.dataStore.edit { it[MODEL] = value }
    suspend fun setAiLimit(value: Int) = context.dataStore.edit { it[AI_LIMIT] = value.coerceIn(1, 200) }
    suspend fun setNewWords(value: Int) = context.dataStore.edit { it[NEW_WORDS] = value.coerceIn(1, 50) }
    suspend fun setReviewWords(value: Int) = context.dataStore.edit { it[REVIEW_WORDS] = value.coerceIn(1, 100) }
    suspend fun setSentences(value: Int) = context.dataStore.edit { it[SENTENCES] = value.coerceIn(1, 30) }
    suspend fun setUpdateRepository(value: String) =
        context.dataStore.edit { it[UPDATE_REPOSITORY] = value.trim() }
    suspend fun setAutoCheckUpdates(value: Boolean) =
        context.dataStore.edit { it[AUTO_CHECK_UPDATES] = value }
    suspend fun setLastUpdateCheckAt(value: Long) =
        context.dataStore.edit { it[LAST_UPDATE_CHECK_AT] = value }
    suspend fun setSpeechMode(value: String) = context.dataStore.edit {
        it[SPEECH_MODE] = value.takeIf { mode -> mode in setOf("AUTO", "ONLINE", "OFFLINE") }
            ?: "AUTO"
    }
    suspend fun setSpeechRate(value: Float) = context.dataStore.edit {
        it[SPEECH_RATE] = value.coerceIn(0.65f, 1.15f)
    }

    suspend fun reset() {
        context.dataStore.edit { it.clear() }
        saveApiKey("")
    }

    private companion object {
        const val STORAGE_UNAVAILABLE = "系统安全存储暂不可用，AI 密钥功能已停用。可继续离线学习；请重启手机后重试。不会将密钥改存为明文。"
        const val KEY_API = "openai_api_key"
        val PROVIDER = stringPreferencesKey("provider")
        val MODEL = stringPreferencesKey("model")
        val AI_LIMIT = intPreferencesKey("daily_ai_limit")
        val NEW_WORDS = intPreferencesKey("daily_new_words")
        val REVIEW_WORDS = intPreferencesKey("daily_review_words")
        val SENTENCES = intPreferencesKey("daily_sentences")
        val TARGET_SCORE = floatPreferencesKey("target_score")
        val UPDATE_REPOSITORY = stringPreferencesKey("update_repository")
        val AUTO_CHECK_UPDATES = booleanPreferencesKey("auto_check_updates")
        val LAST_UPDATE_CHECK_AT = longPreferencesKey("last_update_check_at")
        val SPEECH_MODE = stringPreferencesKey("speech_mode")
        val SPEECH_RATE = floatPreferencesKey("speech_rate")
    }
}
