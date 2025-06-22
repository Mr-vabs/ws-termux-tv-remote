package com.example.wstvremote

import android.app.Activity
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.content.Intent
import okhttp3.*

class MainActivity : Activity() {
    private lateinit var tts: TextToSpeech
    private lateinit var webSocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = OkHttpClient()
        val request = Request.Builder().url("ws://YOUR_SERVER_IP:8080").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                handleCommand(text)
            }
        })

        tts = TextToSpeech(this) {}
    }

    private fun handleCommand(cmd: String) {
        val audio = getSystemService(AUDIO_SERVICE) as AudioManager
        when {
            cmd == "volume_up" -> audio.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
            cmd == "volume_down" -> audio.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
            cmd == "home" -> {
                val i = Intent(Intent.ACTION_MAIN)
                i.addCategory(Intent.CATEGORY_HOME)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            }
            cmd == "open_youtube" -> {
                val intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube.tv")
                startActivity(intent)
            }
            cmd.startsWith("say:") -> {
                val text = cmd.removePrefix("say:")
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }
}