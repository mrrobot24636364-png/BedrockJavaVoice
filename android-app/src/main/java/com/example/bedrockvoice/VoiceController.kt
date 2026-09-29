package com.example.bedrockvoice

import android.content.Context
import com.example.bedrockvoice.audio.MicrophoneCapture
import com.example.bedrockvoice.net.VoiceSocket

class VoiceController(context: Context) {
    private val capture = MicrophoneCapture()
    private var socket: VoiceSocket? = null
    var isConnected = false
        private set
    var onState: ((String) -> Unit)? = null

    fun connect(host: String, port: Int, playerId: String, token: String) {
        socket?.close()
        socket = VoiceSocket(
            url = "ws://$host:$port",
            playerId = playerId,
            token = token,
            onState = {
                isConnected = it.startsWith("Connected")
                onState?.invoke(it)
            },
            onBinary = { /* Future: decode/play received Opus frames */ }
        )
        socket?.connect()
    }

    fun startTalking() {
        socket?.startPtt()
        capture.start { pcm ->
            socket?.sendPcm(pcm)
        }
    }

    fun stopTalking() {
        capture.stop()
        socket?.stopPtt()
    }
}
