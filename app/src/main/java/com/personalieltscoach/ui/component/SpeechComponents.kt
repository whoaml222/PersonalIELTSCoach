package com.personalieltscoach.ui.component

import android.content.Intent
import android.media.AudioAttributes
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
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
import com.personalieltscoach.data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

class SpeechController internal constructor(
    private val application: CoachApplication,
    private val settingsRepository: SettingsRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val mainHandler = Handler(Looper.getMainLooper())
    private val utteranceCounter = AtomicLong()
    private var engine: TextToSpeech? = null
    private var currentMode = "AUTO"
    private var currentRate = 0.92f
    private var pendingText: String? = null
    private var lastText: String? = null
    private var retriedOffline = false

    var status by mutableStateOf(SpeechStatus.INITIALIZING)
        private set

    var lastError by mutableStateOf<String?>(null)
        private set

    var selectedVoiceLabel by mutableStateOf("正在加载英国英语声音…")
        private set

    val isReady: Boolean
        get() = status == SpeechStatus.READY || status == SpeechStatus.PLAYING

    init {
        engine = TextToSpeech(application) { result ->
            if (result == TextToSpeech.SUCCESS) {
                engine?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                        .build()
                )
                engine?.setOnUtteranceProgressListener(progressListener)
                configureVoice()
                pendingText?.let {
                    pendingText = null
                    speak(it)
                }
            } else {
                showError("手机朗读服务初始化失败，请检查系统语音设置")
            }
        }
        scope.launch {
            settingsRepository.settings.collectLatest { settings ->
                currentMode = settings.speechMode
                currentRate = settings.speechRate
                engine?.setSpeechRate(currentRate)
                if (engine != null) configureVoice()
            }
        }
    }

    fun speak(text: String) {
        val normalized = text.trim().take(TextToSpeech.getMaxSpeechInputLength())
        if (normalized.isBlank()) return
        if (status == SpeechStatus.INITIALIZING || engine == null) {
            pendingText = normalized
            return
        }
        if (status == SpeechStatus.ERROR) configureVoice()
        lastText = normalized
        retriedOffline = false
        speakInternal(normalized)
    }

    fun installOfflineVoice() {
        runCatching {
            application.startActivity(
                Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }.onFailure {
            Toast.makeText(application, "无法打开语音下载页面，请到系统设置中下载英国英语语音", Toast.LENGTH_LONG).show()
        }
    }

    internal fun release() {
        pendingText = null
        engine?.stop()
        engine?.shutdown()
        engine = null
        scope.cancel()
    }

    private fun speakInternal(text: String) {
        val id = "coach-${utteranceCounter.incrementAndGet()}"
        val result = engine?.speak(text, TextToSpeech.QUEUE_FLUSH, Bundle(), id)
        if (result == TextToSpeech.ERROR) handleSpeechError()
    }

    private fun configureVoice() {
        val tts = engine ?: return
        tts.setSpeechRate(currentRate)
        val candidates = tts.voices.orEmpty()
            .filter { voice ->
                voice.locale.language.equals("en", ignoreCase = true) &&
                    voice.locale.country.equals("GB", ignoreCase = true) &&
                    TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED !in voice.features
            }
        val network = candidates.filter(Voice::isNetworkConnectionRequired).bestVoice()
        val offline = candidates.filterNot(Voice::isNetworkConnectionRequired).bestVoice()
        val selected = when (currentMode) {
            "ONLINE" -> network ?: offline
            "OFFLINE" -> offline ?: network
            else -> network ?: offline
        }
        if (selected != null) {
            tts.voice = selected
            selectedVoiceLabel = when {
                selected.isNetworkConnectionRequired && currentMode == "OFFLINE" ->
                    "未找到离线英音，暂用联网英国英语"
                selected.isNetworkConnectionRequired -> "联网优质英国英语 · 无 Token"
                currentMode == "ONLINE" -> "联网英音不可用，已切换离线英国英语"
                else -> "离线英国英语 · 无 Token"
            }
        } else {
            val result = tts.setLanguage(Locale.UK)
            selectedVoiceLabel = if (
                result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                "缺少英国英语语音，请下载离线语音包"
            } else {
                "英国英语默认声音 · 无 Token"
            }
        }
        lastError = null
        status = SpeechStatus.READY
    }

    private fun List<Voice>.bestVoice(): Voice? = sortedWith(
        compareByDescending<Voice> { it.quality }
            .thenBy { it.latency }
            .thenBy { it.name }
    ).firstOrNull()

    private fun selectOfflineFallback(): Boolean {
        val tts = engine ?: return false
        val offline = tts.voices.orEmpty()
            .filter { voice ->
                voice.locale.language.equals("en", ignoreCase = true) &&
                    voice.locale.country.equals("GB", ignoreCase = true) &&
                    !voice.isNetworkConnectionRequired &&
                    TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED !in voice.features
            }
            .bestVoice()
            ?: return false
        tts.voice = offline
        selectedVoiceLabel = "网络不可用，已自动切换离线英国英语"
        status = SpeechStatus.READY
        return true
    }

    private fun handleSpeechError() {
        mainHandler.post {
            val text = lastText
            if (!retriedOffline && engine?.voice?.isNetworkConnectionRequired == true && text != null) {
                retriedOffline = true
                if (selectOfflineFallback()) {
                    speakInternal(text)
                    return@post
                }
            }
            showError("朗读失败，请检查系统英国英语语音是否已安装")
        }
    }

    private fun showError(message: String) {
        lastError = message
        status = SpeechStatus.ERROR
        Toast.makeText(application, message, Toast.LENGTH_LONG).show()
    }

    private val progressListener = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            mainHandler.post { status = SpeechStatus.PLAYING }
        }

        override fun onDone(utteranceId: String?) {
            mainHandler.post {
                retriedOffline = false
                status = SpeechStatus.READY
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onError(utteranceId: String?) = handleSpeechError()

        override fun onError(utteranceId: String?, errorCode: Int) = handleSpeechError()
    }
}

enum class SpeechStatus {
    INITIALIZING,
    READY,
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
                    contentDescription = "朗读整句"
                )
            }
        }
        if (showHint) {
            Text(
                "点击英文单词可单独发音；在线英音不可用时自动切换离线英音，不消耗 Token。",
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
