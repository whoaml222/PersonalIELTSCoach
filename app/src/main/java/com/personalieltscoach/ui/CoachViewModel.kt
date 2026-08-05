package com.personalieltscoach.ui

import android.app.DownloadManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.personalieltscoach.AppContainer
import com.personalieltscoach.data.local.dao.ActivityTotal
import com.personalieltscoach.data.local.entity.*
import com.personalieltscoach.data.repository.CoachSettings
import com.personalieltscoach.domain.model.*
import com.personalieltscoach.domain.service.PlacementEvaluator
import com.personalieltscoach.domain.service.SentencePackStats
import com.personalieltscoach.domain.service.SentenceRating
import com.personalieltscoach.update.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString

data class AsyncResult<T>(
    val loading: Boolean = false,
    val value: T? = null,
    val error: String? = null,
    val fromCache: Boolean = false,
    val requestKey: String? = null
)

data class SentencePackSessionState(
    val loading: Boolean = false,
    val cards: List<SentenceCardEntity> = emptyList(),
    val initialCount: Int = 0,
    val minutes: Int = 5,
    val answering: Boolean = false,
    val completed: Boolean = false,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class CoachViewModel(private val container: AppContainer) : ViewModel() {
    private val coach = container.coachRepository
    private val ai = container.aiRepository
    private val settingsRepository = container.settingsRepository
    private val updates = container.updateRepository
    private val updateDownloads = container.updateDownloadManager

    private val _ready = MutableStateFlow(false)
    val ready = _ready.asStateFlow()
    private val _startupHasProfile = MutableStateFlow<Boolean?>(null)
    val startupHasProfile = _startupHasProfile.asStateFlow()
    private val currentDate = MutableStateFlow(com.personalieltscoach.data.repository.CoachRepository.today())
    val profile = coach.profile.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val plan = currentDate.flatMapLatest(coach::plan)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val tasks = currentDate.flatMapLatest(coach::tasks)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val wrongWords = coach.wrongWords.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val allWords = coach.allWords.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val readings = coach.readings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val writingRecords = coach.writingRecords.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val settings = settingsRepository.settings.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CoachSettings()
    )
    val todayUsage = currentDate.flatMapLatest(ai::usage)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val masteredCount = coach.masteredCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val learningCount = combine(coach.learningCount, coach.reviewingCount) { a, b -> a + b }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val wrongCount = coach.wrongCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val sentencePackStats = coach.sentencePackStats
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SentencePackStats())

    private val activityTotals = currentDate.flatMapLatest(coach::totals)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    private val activityMinutes = currentDate.flatMapLatest(coach::minutes)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val dailyStats = combine(activityTotals, activityMinutes) { totals, minutes ->
        DailyStats(
            minutes = minutes,
            newWords = totals.amount("NEW_WORD"),
            reviewedWords = totals.amount("REVIEW_WORD"),
            wrongWords = totals.amount("WRONG_WORD"),
            sentences = totals.amount("SENTENCE"),
            readingWords = totals.amount("READING_WORD"),
            writingSentences = totals.amount("WRITING")
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DailyStats())

    private val _questions = MutableStateFlow<List<PlacementQuestionEntity>>(emptyList())
    val questions = _questions.asStateFlow()
    private val _newWords = MutableStateFlow<List<WordItemEntity>>(emptyList())
    val newWords = _newWords.asStateFlow()
    private val _dueWords = MutableStateFlow<List<WordItemEntity>>(emptyList())
    val dueWords = _dueWords.asStateFlow()
    private val _sentenceResult = MutableStateFlow(AsyncResult<SentenceAnalysisResult>())
    val sentenceResult = _sentenceResult.asStateFlow()
    private val _writingResult = MutableStateFlow(AsyncResult<WritingCorrectionResult>())
    val writingResult = _writingResult.asStateFlow()
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()
    private val _connectionState = MutableStateFlow(AsyncResult<Boolean>())
    val connectionState = _connectionState.asStateFlow()
    private val _updateState = MutableStateFlow(UpdateUiState())
    val updateState = _updateState.asStateFlow()
    private val _sentencePackSession = MutableStateFlow(SentencePackSessionState())
    val sentencePackSession = _sentencePackSession.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { coach.initializeIfNeeded() }
                .onFailure { _message.value = "初始化失败：${it.userMessage()}" }
            _startupHasProfile.value = runCatching { coach.hasProfile() }.getOrDefault(false)
            _ready.value = true
            if (!restorePendingUpdate()) {
                checkForUpdate(manual = false)
            }
        }
    }

    fun loadPlacementQuestions() {
        viewModelScope.launch {
            _questions.value = coach.placementQuestions()
        }
    }

    fun completePlacement(correct: Int, onDone: () -> Unit) {
        viewModelScope.launch {
            runCatching { coach.savePlacement(PlacementEvaluator.evaluate(correct)) }
                .onSuccess {
                    _startupHasProfile.value = true
                    onDone()
                }
                .onFailure { _message.value = it.userMessage() }
        }
    }

    fun loadNewWords() {
        viewModelScope.launch { _newWords.value = coach.newWords() }
    }

    fun loadDueWords() {
        viewModelScope.launch { _dueWords.value = coach.dueWords() }
    }

    fun answerWord(
        word: WordItemEntity,
        correct: Boolean,
        review: Boolean,
        reloadSession: Boolean = true
    ) {
        viewModelScope.launch {
            runCatching { coach.answerWord(word, correct, review) }
                .onSuccess {
                    if (reloadSession) {
                        if (review) loadDueWords() else loadNewWords()
                    }
                    _message.value = if (correct) {
                        "答对了，已安排下次复习"
                    } else {
                        "已加入错词本，明天再复习"
                    }
                }
                .onFailure { _message.value = it.userMessage() }
        }
    }

    fun analyzeSentence(sentence: String) {
        val requestKey = sentence.trim()
        viewModelScope.launch {
            _sentenceResult.value = AsyncResult(loading = true, requestKey = requestKey)
            runCatching { ai.analyzeSentence(requestKey) }
                .onSuccess { (result, cached) ->
                    _sentenceResult.value = AsyncResult(
                        value = result,
                        fromCache = cached,
                        requestKey = requestKey
                    )
                    coach.recordSentenceStudy(requestKey)
                }
                .onFailure {
                    _sentenceResult.value = AsyncResult(
                        error = it.userMessage(),
                        requestKey = requestKey
                    )
                }
        }
    }

    fun startSentencePackSession(minutes: Int) {
        val normalizedMinutes = minutes.coerceIn(3, 20)
        val limit = when (normalizedMinutes) {
            in 3..4 -> 3
            in 5..9 -> 5
            in 10..19 -> 8
            else -> 12
        }
        viewModelScope.launch {
            _sentencePackSession.value = SentencePackSessionState(
                loading = true,
                minutes = normalizedMinutes
            )
            runCatching { coach.sentenceSession(limit) }
                .onSuccess { cards ->
                    _sentencePackSession.value = SentencePackSessionState(
                        cards = cards,
                        initialCount = cards.size,
                        minutes = normalizedMinutes,
                        completed = cards.isEmpty()
                    )
                }
                .onFailure { error ->
                    _sentencePackSession.value = SentencePackSessionState(
                        minutes = normalizedMinutes,
                        error = error.userMessage()
                    )
                }
        }
    }

    fun rateCurrentSentence(rating: SentenceRating) {
        val state = _sentencePackSession.value
        val card = state.cards.firstOrNull() ?: return
        if (state.answering) return
        _sentencePackSession.value = state.copy(answering = true, error = null)
        viewModelScope.launch {
            runCatching { coach.answerSentence(card, rating) }
                .onSuccess {
                    val remaining = _sentencePackSession.value.cards.drop(1)
                    _sentencePackSession.value = _sentencePackSession.value.copy(
                        cards = remaining,
                        answering = false,
                        completed = remaining.isEmpty()
                    )
                }
                .onFailure { error ->
                    _sentencePackSession.value = _sentencePackSession.value.copy(
                        answering = false,
                        error = error.userMessage()
                    )
                }
        }
    }

    fun closeSentencePackSession() {
        _sentencePackSession.value = SentencePackSessionState()
    }

    fun saveSentence(sentence: String) {
        val normalized = sentence.trim()
        val state = sentenceResult.value
        val result = state.value ?: return
        if (state.requestKey != normalized) {
            _message.value = "句子已改变，请重新分析后再加入复习"
            return
        }
        viewModelScope.launch {
            coach.saveSentence(normalized, result.translation)
            _message.value = "已加入句子复习"
        }
    }

    fun correctWriting(prompt: String, text: String) {
        val normalizedText = text.trim()
        val requestKey = "$prompt|$normalizedText"
        viewModelScope.launch {
            _writingResult.value = AsyncResult(loading = true, requestKey = requestKey)
            val level = profile.value?.currentLevel ?: "A0-A1"
            runCatching { ai.correctWriting(prompt, normalizedText, level) }
                .onSuccess { (result, cached) ->
                    _writingResult.value = AsyncResult(
                        value = result,
                        fromCache = cached,
                        requestKey = requestKey
                    )
                    coach.recordWriting(prompt, normalizedText)
                }
                .onFailure {
                    _writingResult.value = AsyncResult(
                        error = it.userMessage(),
                        requestKey = requestKey
                    )
                }
        }
    }

    fun recordReading(text: String) {
        viewModelScope.launch {
            coach.recordReading(text)
            _message.value = "本次阅读已记录"
        }
    }

    suspend fun findWord(word: String): WordItemEntity? = coach.findWord(word)

    fun addUnknownWord(word: String) {
        viewModelScope.launch {
            coach.addUnknownWord(word)
            _message.value = "$word 已加入单词本"
        }
    }

    fun saveApiKey(key: String) {
        settingsRepository.saveApiKey(key)
        _message.value = "API Key 已安全保存在本机"
    }

    fun setModel(value: String) = viewModelScope.launch { settingsRepository.setModel(value) }
    fun setAiLimit(value: Int) = viewModelScope.launch { settingsRepository.setAiLimit(value) }
    fun setNewWords(value: Int) = viewModelScope.launch { settingsRepository.setNewWords(value) }
    fun setReviewWords(value: Int) = viewModelScope.launch { settingsRepository.setReviewWords(value) }
    fun setSentences(value: Int) = viewModelScope.launch { settingsRepository.setSentences(value) }
    fun setSpeechMode(value: String) = viewModelScope.launch { settingsRepository.setSpeechMode(value) }
    fun setSpeechRate(value: Float) = viewModelScope.launch { settingsRepository.setSpeechRate(value) }
    fun saveUpdateSettings(repository: String, autoCheck: Boolean) {
        viewModelScope.launch {
            settingsRepository.setUpdateRepository(repository)
            settingsRepository.setAutoCheckUpdates(autoCheck)
            _message.value = "更新设置已保存"
            if (repository.isNotBlank()) checkForUpdate(manual = true)
        }
    }

    fun checkForUpdate(manual: Boolean = true) {
        viewModelScope.launch {
            val currentSettings = settingsRepository.current()
            if (currentSettings.updateRepository.isBlank()) {
                if (manual) _message.value = "请先填写 GitHub 更新仓库"
                return@launch
            }
            val now = System.currentTimeMillis()
            if (!manual && (
                    !currentSettings.autoCheckUpdates ||
                        now - currentSettings.lastUpdateCheckAt < UPDATE_CHECK_INTERVAL_MS
                    )
            ) return@launch
            _updateState.value = _updateState.value.copy(checking = true, error = null)
            runCatching { updates.check(currentSettings.updateRepository) }
                .onSuccess { update ->
                    settingsRepository.setLastUpdateCheckAt(now)
                    _updateState.value = UpdateUiState(available = update)
                    if (manual && update == null) _message.value = "当前已是最新版本"
                }
                .onFailure {
                    _updateState.value = UpdateUiState(error = it.userMessage())
                    if (manual) _message.value = it.userMessage()
                }
        }
    }

    fun dismissUpdate() {
        _updateState.value = UpdateUiState()
    }

    fun downloadUpdate() {
        val update = updateState.value.available ?: return
        viewModelScope.launch {
            runCatching { updateDownloads.enqueue(update) }
                .onSuccess { pending ->
                    _updateState.value = _updateState.value.copy(
                        downloading = true,
                        downloadProgress = 0,
                        downloadedFile = null,
                        error = null
                    )
                    monitorDownload(pending)
                }
                .onFailure {
                    _updateState.value = _updateState.value.copy(error = it.userMessage())
                }
        }
    }

    fun cancelUpdateDownload() {
        updateDownloads.pending()?.let { updateDownloads.cancel(it.downloadId) }
        _updateState.value = UpdateUiState()
    }

    fun installDownloadedUpdate() {
        val filePath = updateState.value.downloadedFile
            ?: updateDownloads.pending()?.filePath
            ?: return
        UpdateInstaller.openInstallFlow(container.application, filePath)
    }

    fun testConnection() {
        viewModelScope.launch {
            _connectionState.value = AsyncResult(loading = true)
            runCatching { ai.testConnection() }
                .onSuccess { _connectionState.value = AsyncResult(value = it) }
                .onFailure { _connectionState.value = AsyncResult(error = it.userMessage()) }
        }
    }

    fun clearAiCache() {
        viewModelScope.launch {
            ai.clearCache()
            _message.value = "AI 分析缓存已清除"
        }
    }

    fun clearLearningData(onDone: () -> Unit) {
        viewModelScope.launch {
            coach.clearLearningData()
            _startupHasProfile.value = false
            _sentenceResult.value = AsyncResult()
            _writingResult.value = AsyncResult()
            _sentencePackSession.value = SentencePackSessionState()
            _message.value = "学习数据已清空"
            onDone()
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun refreshDate() {
        val newDate = com.personalieltscoach.data.repository.CoachRepository.today()
        if (newDate == currentDate.value) return
        viewModelScope.launch {
            coach.ensureTodayPlan()
            currentDate.value = newDate
        }
    }

    fun decodeOptions(value: String): List<String> =
        runCatching { container.json.decodeFromString<List<String>>(value) }.getOrDefault(emptyList())

    private fun List<ActivityTotal>.amount(type: String): Int =
        firstOrNull { it.type == type }?.amount ?: 0

    private fun Throwable.userMessage(): String =
        message?.takeIf(String::isNotBlank) ?: "发生未知错误，请稍后重试"

    private fun restorePendingUpdate(): Boolean {
        val pending = updateDownloads.pending() ?: return false
        if (!VersionComparator.isNewer(pending.version, com.personalieltscoach.BuildConfig.VERSION_NAME)) {
            updateDownloads.clearPending(removeFile = true)
            return false
        }
        viewModelScope.launch { monitorDownload(pending) }
        return true
    }

    private suspend fun monitorDownload(pending: PendingUpdateDownload) {
        while (true) {
            val progress = updateDownloads.progress(pending.downloadId)
            if (progress == null) {
                _updateState.value = UpdateUiState(error = "找不到更新下载任务")
                return
            }
            when (progress.status) {
                DownloadManager.STATUS_PENDING,
                DownloadManager.STATUS_PAUSED,
                DownloadManager.STATUS_RUNNING -> {
                    _updateState.value = _updateState.value.copy(
                        downloading = true,
                        downloadProgress = progress.percent,
                        error = null
                    )
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    when (val verification =
                        UpdatePackageVerifier(container.application).verify(pending.filePath)
                    ) {
                        UpdateVerificationResult.Valid -> {
                            _updateState.value = _updateState.value.copy(
                                downloading = false,
                                downloadProgress = 100,
                                downloadedFile = pending.filePath,
                                error = null
                            )
                        }
                        is UpdateVerificationResult.Invalid -> {
                            updateDownloads.clearPending(removeFile = true)
                            _updateState.value = UpdateUiState(error = verification.message)
                        }
                    }
                    return
                }
                DownloadManager.STATUS_FAILED -> {
                    updateDownloads.clearPending(removeFile = true)
                    _updateState.value = UpdateUiState(
                        error = "更新下载失败（错误码 ${progress.reason}）"
                    )
                    return
                }
            }
            delay(1_000)
        }
    }

    private companion object {
        const val UPDATE_CHECK_INTERVAL_MS = 12L * 60L * 60L * 1000L
    }
}

class CoachViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CoachViewModel(container) as T
}
