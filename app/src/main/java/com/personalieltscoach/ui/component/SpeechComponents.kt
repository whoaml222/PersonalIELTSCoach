package com.personalieltscoach.ui.component

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

    internal fun release() {
        requestId += 1
        requestJob?.cancel()
        requestJob = null
        stopPlayer()
        scope.cancel()
    }

    private fun requestSpeech(text: String, retryCount: Int) {
        val normalized = text.trim().take(MAX_INPUT_CHARACTERS)
        if (normalized.isBlank()) return
        requestId += 1
        val currentRequest = requestId
        requestJob?.cancel()
        stopPlayer()
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
            applyPlaybackRate(prepared)
            status = SpeechStatus.PLAYING
            prepared.start()
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
    DisposableEffect(controller) {
        onDispose(controller::release)
    }
    return controller
}

@Composable
fun SpokenEnglishText(
    text: String,
    speech: SpeechController,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    wordColor: Color = MaterialTheme.colorScheme.primary,
    showHint: Boolean = false
) {
    if (text.isBlank()) return
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
                onWordClick = speech::speak,
                modifier = Modifier.weight(1f),
                style = style,
                wordColor = wordColor
            )
            IconButton(
                onClick = { speech.speak(text) },
                enabled = speech.isReady
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "使用英音词典朗读"
                )
            }
        }
        if (showHint) {
            Text(
                "点击单词或扬声器使用在线英音词典；播放过的音频会缓存，可离线重听。",
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
    onWordClick: (String) -> Unit,
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
        modifier = modifier,
        style = style,
        onClick = { offset ->
            matches.firstOrNull { offset in it.range }?.value?.let(onWordClick)
        }
    )
}

private val ENGLISH_WORD = Regex("[A-Za-z]+(?:['’][A-Za-z]+)*")
