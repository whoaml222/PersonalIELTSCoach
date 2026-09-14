package com.personalieltscoach.speech

import android.content.*
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import androidx.core.content.ContextCompat

/** One app playback owner, with OS interruption/headphone-unplug protection. Main-thread only. */
class AudioFocusGuard(context: Context, private val onInterrupt: () -> Unit) {
    private val application = context.applicationContext
    private val manager = application.getSystemService(AudioManager::class.java)
    private val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        .setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build())
        .setWillPauseWhenDucked(true)
        .setOnAudioFocusChangeListener { if (it < 0) onInterrupt() }
        .build()
    private var registered = false
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) onInterrupt()
        }
    }
    fun acquire(): Boolean {
        if (owner !== this) owner?.onInterrupt?.invoke()
        if (manager.requestAudioFocus(request) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) return false
        owner = this
        if (!registered) {
            ContextCompat.registerReceiver(application, receiver,
                IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY), ContextCompat.RECEIVER_NOT_EXPORTED)
            registered = true
        }
        return true
    }
    fun release() {
        manager.abandonAudioFocusRequest(request)
        if (owner === this) owner = null
        if (registered) { application.unregisterReceiver(receiver); registered = false }
    }
    companion object { private var owner: AudioFocusGuard? = null }
}
