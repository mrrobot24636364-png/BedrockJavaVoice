package com.example.bedrockvoice.audio

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.concurrent.thread

class MicrophoneCapture {
    private val sampleRate = 48000
    private val channel = AudioFormat.CHANNEL_IN_MONO
    private val encoding = AudioFormat.ENCODING_PCM_16BIT
    private var record: AudioRecord? = null
    @Volatile private var running = false

    fun start(onFrame: (ByteArray) -> Unit) {
        if (running) return
        val min = AudioRecord.getMinBufferSize(sampleRate, channel, encoding)
        record = AudioRecord(
            MediaRecorder.AudioSource.VOICE_COMMUNICATION,
            sampleRate, channel, encoding, maxOf(min, 1920 * 2)
        )
        record?.startRecording()
        running = true

        thread(name = "voice-mic") {
            val buffer = ByteArray(1920) // 20 ms mono PCM16 @ 48 kHz
            while (running) {
                val n = record?.read(buffer, 0, buffer.size) ?: 0
                if (n > 0) onFrame(buffer.copyOf(n))
            }
        }
    }

    fun stop() {
        running = false
        try { record?.stop() } catch (_: Exception) {}
        record?.release()
        record = null
    }
}
