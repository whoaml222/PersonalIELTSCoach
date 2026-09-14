package com.personalieltscoach.ui.component

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.personalieltscoach.speech.AudioFocusGuard
import kotlinx.coroutines.delay
import java.io.File

private class ReadingAudio(context: android.content.Context, file: File, private val startAt: Int) {
    var ready by mutableStateOf(false)
    var playing by mutableStateOf(false)
    var duration by mutableStateOf(0)
    var position by mutableStateOf(startAt)
    var error by mutableStateOf<String?>(null)
    var speed by mutableStateOf(1f)
    var repeat by mutableStateOf(false)
    private var released = false
    private val focus = AudioFocusGuard(context) { pause() }
    private val player = MediaPlayer()
    init {
        player.setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build())
        player.setOnPreparedListener {
            duration = it.duration.coerceAtLeast(0)
            position = startAt.coerceIn(0, duration)
            it.seekTo(position)
            ready = true
        }
        player.setOnCompletionListener {
            playing = false
            position = duration
            if (repeat) { seek(0); toggle() } else focus.release()
        }
        player.setOnErrorListener { _, _, _ ->
            playing = false; ready = false; error = "原音频无法播放，请重新导入完整课程包。"
            focus.release(); true
        }
        runCatching { player.setDataSource(file.absolutePath); player.prepareAsync() }
            .onFailure { error = "音频文件无法打开。" }
    }
    fun tick() { if (ready && !released) runCatching { position = player.currentPosition } }
    fun toggle() {
        if (!ready || released) return
        if (playing) { pause(); return }
        if (!focus.acquire()) { error = "其他应用正在使用音频，请稍后播放。"; return }
        runCatching {
            if (position >= duration) seek(0)
            player.start(); player.playbackParams = PlaybackParams().setSpeed(speed).setPitch(1f)
            playing = true; error = null
        }.onFailure { focus.release(); error = "播放失败，请重新打开本篇课文。" }
    }
    fun pause() {
        if (ready && !released) runCatching { if (player.isPlaying) player.pause(); tick() }
        playing = false; focus.release()
    }
    fun seek(value: Int) {
        if (!ready || released) return
        position = value.coerceIn(0, duration)
        runCatching { player.seekTo(position) }
    }
    fun changeSpeed(value: Float) {
        speed = value
        if (playing) runCatching { player.playbackParams = PlaybackParams().setSpeed(speed).setPitch(1f) }
    }
    fun release() { pause(); released = true; player.release() }
}

@Composable
fun LocalReadingAudio(file: File, initialPosition: Int, onPosition: (Int) -> Unit) {
    val context = LocalContext.current
    val audio = remember(file.absolutePath) { ReadingAudio(context, file, initialPosition) }
    val save by rememberUpdatedState(onPosition)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(audio, lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) { audio.pause(); save(audio.position) }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer); audio.tick(); save(audio.position); audio.release() }
    }
    LaunchedEffect(audio) {
        while (true) {
            delay(1000)
            if (audio.playing) { audio.tick(); save(audio.position) }
        }
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("课文原音频 · 已离线保存", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text("保留原录音口音；不标注为标准英音。", style = MaterialTheme.typography.bodySmall)
        Slider(value = audio.position.toFloat().coerceIn(0f, audio.duration.toFloat().coerceAtLeast(1f)),
            onValueChange = { audio.seek(it.toInt()); save(audio.position) },
            valueRange = 0f..audio.duration.toFloat().coerceAtLeast(1f), enabled = audio.ready)
        Text("${formatTime(audio.position)} / ${formatTime(audio.duration)}", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { audio.toggle(); save(audio.position) }, enabled = audio.ready) { Text(if (audio.playing) "暂停" else "播放") }
            TextButton(onClick = { audio.changeSpeed(when(audio.speed) { .8f -> 1f; 1f -> 1.2f; else -> .8f }) }) { Text("${audio.speed}×") }
            FilterChip(selected = audio.repeat, onClick = { audio.repeat = !audio.repeat }, label = { Text("循环本篇") })
        }
        if (!audio.ready && audio.error == null) LinearProgressIndicator(Modifier.fillMaxWidth())
        audio.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
private fun formatTime(ms: Int): String = "%d:%02d".format(ms / 60000, (ms / 1000) % 60)
