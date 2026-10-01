package com.personalieltscoach.ui.component

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.onClick
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personalieltscoach.CoachApplication
import com.personalieltscoach.data.repository.SettingsRepository
import com.personalieltscoach.speech.DictionarySpeechService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class SpeechController internal constructor(
    private val application: CoachApplication,
    private val settingsRepository: SettingsRepository,
    private val service: DictionarySpeechService = DictionarySpeechService(application)
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var requestJob: Job? = null
    private var player: MediaPlayer? = null
    private var requestId = 0L
    private var currentRate = 0.92f
    private val audioFocus = com.personalieltscoach.speech.AudioFocusGuard(application) { stop() }
    var currentText by mutableStateOf("")
        private set

    var status by mutableStateOf(SpeechStatus.READY)
        private set

    var lastError by mutableStateOf<String?>(null)
        private set

    val selectedVoiceLabel: String
        get() = "在线英音词典 · 播放后自动离线缓存"

    val isReady: Boolean
        get() = status != SpeechStatus.LOADING

    init {
        scope.launch {
            settingsRepository.settings.collectLatest { settings ->
                currentRate = settings.speechRate
                player?.let(::applyPlaybackRate)
            }
        }
    }

    fun speak(text: String) {
        requestSpeech(text, retryCount = 0)
    }

    fun stop() {
        requestId += 1
        requestJob?.cancel()
        requestJob = null
        stopPlayer()
        audioFocus.release()
        currentText = ""
        status = SpeechStatus.READY
    }

    internal fun release() {
        stop()
        scope.cancel()
    }

    private fun requestSpeech(text: String, retryCount: Int) {
        val normalized = text.trim()
        if (normalized.isBlank()) return
        if (normalized.length > MAX_INPUT_CHARACTERS) {
            stop()
            showError("这段文字过长，请分句朗读，或播放课文原录音（单次上限 500 字符）")
            return
        }
        requestId += 1
        val currentRequest = requestId
        requestJob?.cancel()
        stopPlayer()
        audioFocus.release()
        currentText = normalized
        lastError = null
        status = SpeechStatus.LOADING
        requestJob = scope.launch {
            try {
                val files = service.prepare(normalized)
                if (currentRequest == requestId) {
                    playQueue(files, normalized, currentRequest, index = 0, retryCount = retryCount)
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Throwable) {
                if (currentRequest == requestId) showError(error.userSpeechMessage())
            }
        }
    }

    private fun playQueue(
        files: List<File>,
        originalText: String,
        currentRequest: Long,
        index: Int,
        retryCount: Int
    ) {
        if (currentRequest != requestId) return
        if (index >= files.size) {
            status = SpeechStatus.READY
            currentText = ""
            audioFocus.release()
            return
        }
        val file = files[index]
        val next = MediaPlayer()
        player = next
        next.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        next.setOnPreparedListener { prepared ->
            if (currentRequest != requestId) {
                prepared.release()
                return@setOnPreparedListener
            }
            if (!audioFocus.acquire()) {
                stopPlayer()
                showError("当前音频正在被其他应用使用，请稍后重试")
            } else {
                runCatching { prepared.start(); applyPlaybackRate(prepared) }
                    .onSuccess { status = SpeechStatus.PLAYING }
                    .onFailure { stopPlayer(); showError("音频无法开始播放，请重试") }
            }
        }
        next.setOnCompletionListener { completed ->
            completed.release()
            if (player === completed) player = null
            playQueue(files, originalText, currentRequest, index + 1, retryCount)
        }
        next.setOnErrorListener { failed, _, _ ->
            failed.release()
            if (player === failed) player = null
            service.invalidate(listOf(file))
            if (currentRequest == requestId && retryCount < 1) {
                requestSpeech(originalText, retryCount + 1)
            } else if (currentRequest == requestId) {
                showError("英音词典播放中断，请检查网络后重试")
            }
            true
        }
        runCatching {
            next.setDataSource(file.absolutePath)
            next.prepareAsync()
        }.onFailure {
            next.release()
            if (player === next) player = null
            service.invalidate(listOf(file))
            if (retryCount < 1) requestSpeech(originalText, retryCount + 1)
            else showError("英音词典音频无法播放，请重试")
        }
    }

    private fun applyPlaybackRate(mediaPlayer: MediaPlayer) {
        runCatching {
            mediaPlayer.playbackParams = PlaybackParams()
                .setSpeed(currentRate.coerceIn(0.65f, 1.15f))
                .setPitch(1.0f)
        }
    }

    private fun stopPlayer() {
        player?.let { current ->
            runCatching { current.stop() }
            current.release()
        }
        player = null
    }

    private fun showError(message: String) {
        audioFocus.release()
        lastError = message
        status = SpeechStatus.ERROR
        Toast.makeText(application, message, Toast.LENGTH_LONG).show()
    }

    private fun Throwable.userSpeechMessage(): String = when (this) {
        is IllegalArgumentException -> message ?: "没有可朗读的英文"
        else -> "英音词典暂时不可用，请检查网络后重试"
    }

    private companion object {
        const val MAX_INPUT_CHARACTERS = 500
    }
}

enum class SpeechStatus {
    READY,
    LOADING,
    PLAYING,
    ERROR
}

@Composable
fun rememberSpeechController(): SpeechController {
    val application = LocalContext.current.applicationContext as CoachApplication
    val controller = remember(application) {
        SpeechController(application, application.container.settingsRepository)
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(controller, lifecycle) {
        val observer = LifecycleEventObserver { _, event -> if (event == Lifecycle.Event.ON_STOP) controller.stop() }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer); controller.release() }
    }
    return controller
}

@Composable
fun SpeechButton(
    text: String,
    speech: SpeechController,
    modifier: Modifier = Modifier,
    contentDescription: String = "使用英音词典朗读"
) {
    val active = speech.currentText == text.trim() &&
        speech.status in setOf(SpeechStatus.LOADING, SpeechStatus.PLAYING)
    FilledTonalIconButton(
        onClick = { if (active) speech.stop() else speech.speak(text) },
        enabled = text.isNotBlank(),
        modifier = modifier.size(48.dp).semantics {
            this.contentDescription = if (active) "停止朗读" else contentDescription
        }
    ) {
        if (active && speech.status == SpeechStatus.LOADING)
            CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
        else Icon(if (active) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun SpokenEnglishText(
    text: String,
    speech: SpeechController,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    wordColor: Color = MaterialTheme.colorScheme.primary,
    showHint: Boolean = false,
    contextMeanings: Map<String, String> = emptyMap(),
    contextPhonetics: Map<String, String> = emptyMap(),
    grammar: List<com.personalieltscoach.domain.service.GrammarToken> = emptyList(),
    onCollect: ((com.personalieltscoach.domain.service.WordContext) -> Unit)? = null
) {
    if (text.isBlank()) return
    var selectedOffset by remember(text) { mutableStateOf<Int?>(null) }
    selectedOffset?.let { offset ->
        val selected = ENGLISH_WORD.findAll(text).firstOrNull { offset in it.range }?.value?.lowercase()
        WordContextSheet(text, offset, speech, onDismiss = { selectedOffset = null },
            contextualMeaning = contextMeanings[selected].orEmpty(), contextualPhonetic = contextPhonetics[selected],
            grammar = grammar, onCollect = onCollect)
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            ClickableEnglishText(
                text = text,
                onWordClick = { selectedOffset = it },
                modifier = Modifier.weight(1f),
                style = style,
                wordColor = wordColor
            )
            SpeechButton(text = text, speech = speech)
        }
        if (showHint) {
            Text(
                "点词看释义与用法 · 点右侧听整句",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun ClickableEnglishText(
    text: String,
    onWordClick: (Int) -> Unit,
    modifier: Modifier,
    style: TextStyle,
    wordColor: Color
) {
    val matches = remember(text) { ENGLISH_WORD.findAll(text).toList() }
    val annotated = remember(text, wordColor) {
        AnnotatedString.Builder(text).apply {
            matches.forEach { match ->
                addStyle(
                    SpanStyle(color = wordColor, fontWeight = FontWeight.Medium),
                    match.range.first,
                    match.range.last + 1
                )
            }
        }.toAnnotatedString()
    }
    ClickableText(
        text = annotated,
        modifier = modifier.semantics {
            // Pointer-only text excludes TalkBack and keyboard users from word lookup.
            onClick(label = "查看句中单词") {
                matches.firstOrNull()?.let { onWordClick(it.range.first) }
                matches.isNotEmpty()
            }
            customActions = matches.distinctBy { it.value.lowercase() }.map { match ->
                CustomAccessibilityAction("查词 ${match.value}") { onWordClick(match.range.first); true }
            }
        },
        style = style,
        onClick = { offset ->
            matches.firstOrNull { offset in it.range }?.let { onWordClick(offset) }
        }
    )
}

private val ENGLISH_WORD = com.personalieltscoach.domain.service.ContextDictionary.tokens
