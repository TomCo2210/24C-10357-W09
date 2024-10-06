package dev.tomco.a24c_10357_w09

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.math.pow


class MyService: Service() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var mediaPlayer: MediaPlayer

    private var counter = 0
    private val runnable = object: Runnable {
        override fun run() {
            playSound()
            progress()
        }
    }

    private fun progress() {
        counter++
        broadcastData()

        if (counter > 100) {
            handler.removeCallbacks(runnable)
            stopSelf()
            return
        }

        handler.postDelayed(runnable, 300)
    }

    private fun broadcastData() {
        val intent = Intent("100_FM")
        intent.putExtra("PROGRESS", counter * 10)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun playSound() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.level_up)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val action = intent?.action
        if (action.equals("RUN")) {
            handler.post(runnable)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}