package com.personalieltscoach.ui.component

import android.speech.tts.TextToSpeech
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
import java.util.Locale
import java.util.UUID

class SpeechController internal constructor() {
    private var engine: TextToSpeech? = null
    private var engineInitialized = false

    var status by mutableStateOf(SpeechStatus.INITIALIZING)
        private set

    val isReady: Boolean
        get() = status == SpeechStatus.READY

    internal fun attach(value: TextToSpeech) {
        engine = value
        if (engineInitialized) configure(value)
    }

    internal fun initialized(status: Int) {
        engineInitialized = status == TextToSpeech.SUCCESS
        if (engineInitialized) {
            engine?.let(::configure)
        } else {
            this.status = SpeechStatus.UNAVAILABLE
        }
    }

    fun speak(text: String) {
        if (!isReady || text.isBlank()) return
        engine?.speak(
            text.trim(),
            TextToSpeech.QUEUE_FLUSH,
            null,
            UUID.randomUUID().toString()
        )
    }

    internal fun release() {
        engine?.stop()
        engine?.shutdown()
        engine = null
        engineInitialized = false
        status = SpeechStatus.INITIALIZING
    }

    private fun configure(value: TextToSpeech) {
        val languageResult = value.setLanguage(Locale.UK)
        if (
            languageResult == TextToSpeech.LANG_MISSING_DATA ||
            languageResult == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            status = SpeechStatus.UNAVAILABLE
            return
        }

        val preferredVoice = runCatching { value.voices.orEmpty() }
            .getOrDefault(emptySet())
            .asSequence()
            .filter { it.locale.language == Locale.ENGLISH.language && it.locale.country == Locale.UK.country }
            .sortedWith(
                compareByDescending<android.speech.tts.Voice> {
                    if (it.isNetworkConnectionRequired) 0 else 1
                }.thenByDescending { it.quality }
                    .thenBy { it.latency }
                    .thenBy { it.name }
            )
            .firstOrNull()

        preferredVoice?.let(value::setVoice)
        value.setSpeechRate(0.9f)
        value.setPitch(1.0f)
        status = SpeechStatus.READY
    }
}

enum class SpeechStatus {
    INITIALIZING,
    READY,
    UNAVAILABLE
}

@Composable
fun rememberSpeechController(): SpeechController {
    val context = LocalContext.current.applicationContext
    val controller = remember(context) { SpeechController() }
    DisposableEffect(context, controller) {
        val engine = TextToSpeech(context, controller::initialized)
        controller.attach(engine)
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
                "点击句中的英文单词可单独发音",
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
