package com.example.bedrockvoice.net

import okhttp3.*
import java.util.concurrent.TimeUnit

class VoiceSocket(
    private val url: String,
    private val playerId: String,
    private val token: String,
    private val onState: (String) -> Unit,
    private val onBinary: (ByteArray) -> Unit
) {
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .pingInterval(20, TimeUnit.SECONDS)
        .build()
    private var ws: WebSocket? = null
    private var sequence = 0L

    fun connect() {
        val req = Request.Builder().url(url).build()
        ws = client.newWebSocket(req, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                onState("Connected")
                webSocket.send("HELLO $playerId $token")
            }
            override fun onMessage(webSocket: WebSocket, bytes: okio.ByteString) {
                onBinary(bytes.toByteArray())
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                onState("Server: $text")
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                onState("Disconnected")
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                onState("Error: ${t.message}")
            }
        })
    }

    fun startPtt() { ws?.send("PTT_START") }
    fun stopPtt() { ws?.send("PTT_STOP") }

    fun sendPcm(pcm: ByteArray) {
        sequence++
        // Development transport: raw PCM binary. Replace with Opus in production.
        ws?.send(okio.ByteString.of(*pcm))
    }

    fun close() { ws?.close(1000, "closed") }
}
