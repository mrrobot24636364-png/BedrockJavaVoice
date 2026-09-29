package com.example.bedrockvoice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var controller: VoiceController
    private lateinit var status: TextView
    private lateinit var talk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = VoiceController(this)

        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 28, 28, 28)
        }
        val host = EditText(this).apply { hint = "Voice server"; setText("10.0.2.2") }
        val port = EditText(this).apply { hint = "Port"; setText("8080") }
        val player = EditText(this).apply { hint = "Player ID"; setText("android-player") }
        val token = EditText(this).apply { hint = "Token"; setText("dev-token") }
        val connect = Button(this).apply { text = "Connect" }
        talk = Button(this).apply { text = "🎤 Hold to Talk"; isEnabled = false }
        status = TextView(this).apply { text = "Disconnected" }

        listOf(host, port, player, token, connect, talk, status).forEach(box::addView)
        setContentView(box)

        connect.setOnClickListener {
            controller.connect(host.text.toString(), port.text.toString().toInt(), player.text.toString(), token.text.toString())
        }
        talk.setOnTouchListener { _, e ->
            when (e.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    controller.startTalking()
                    talk.text = "🔴 Talking..."
                    true
                }
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    controller.stopTalking()
                    talk.text = "🎤 Hold to Talk"
                    true
                }
                else -> false
            }
        }

        controller.onState = { s ->
            runOnUiThread {
                status.text = s
                talk.isEnabled = controller.isConnected
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 100)
        }
    }
}
