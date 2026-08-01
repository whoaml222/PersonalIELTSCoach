package com.personalieltscoach.ui.component

import android.media.AudioAttributes
import android.media.MediaPlayer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personalieltscoach.CoachApplication
import com.personalieltscoach.ai.OpenAiSpeechService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SpeechController internal constructor(
    private val application: CoachApplication,
    private val service: OpenAiSpeechService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var requestJob: Job? = null
    private var player: MediaPlayer? = null
    private var requestId = 0L

    var status by mutableStateOf(SpeechStatus.READY)
        private set

    var lastError by mutableStateOf<String?>(null)
        private set

    val isReady: Boolean
        get() = status != SpeechStatus.GENERATING

    fun speak(text: String) {
        if (text.isBlank()) return
        requestId += 1
        val currentRequest = requestId
        requestJob?.cancel()
        stopPlayer()
        lastError = null
        status = SpeechStatus.GENERATING
        requestJob = scope.launch {
            try {
                val audio = service.synthesize(text)
                if (currentRequest == requestId) play(audio.file.absolutePath, currentRequest)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Throwable) {
                if (currentRequest == requestId) showError(error)
            }
        }
    }

    internal fun release() {
        requestId += 1
        requestJob?.cancel()
        requestJob = null
        stopPlayer()
        scope.cancel()
    }

    private fun play(path: String, currentRequest: Long) {
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
            status = SpeechStatus.PLAYING
            prepared.start()
        }
        next.setOnCompletionListener { completed ->
            completed.release()
            if (player === completed) player = null
            if (currentRequest == requestId) status = SpeechStatus.READY
        }
        next.setOnErrorListener { failed, _, _ ->
            failed.release()
            if (player === failed) player = null
            if (currentRequest == requestId) {
                showError(IllegalStateException("Marin 语音播放失败，请重试"))
            }
            true
        }
        runCatching {
            next.setDataSource(path)
            next.prepareAsync()
        }.onFailure { error ->
            next.release()
            if (player === next) player = null
            if (currentRequest == requestId) showError(error)
        }
    }

    private fun stopPlayer() {
        player?.let { current ->
            runCatching { current.stop() }
            current.release()
        }
        player = null
    }

    private fun showError(error: Throwable) {
        val message = error.message?.takeIf(String::isNotBlank)
            ?: "Marin 语音暂时不可用，请稍后重试"
        lastError = message
        status = SpeechStatus.ERROR
        Toast.makeText(application, message, Toast.LENGTH_LONG).show()
    }
}

enum class SpeechStatus {
    READY,
    GENERATING,
    PLAYING,
    ERROR
}

@Composable
fun rememberSpeechController(): SpeechController {
    val application = LocalContext.current.applicationContext as CoachApplication
    val controller = remember(application) {
        SpeechController(application, application.container.speechService)
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
            verticalAlignment = Alignment.CenterVertically
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
                    contentDescription = "使用 Marin 朗读整句"
                )
            }
        }
        if (showHint) {
            Text(
                "点击英文单词可单独发音；首次生成 Marin 语音需要联网",
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

private val ENGLISH_WORD = Regex("[A-Za-z]+(?:['’-][A-Za-z]+)*")
