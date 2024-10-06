package dev.tomco.a24c_10357_w09

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dev.tomco.a24c_10357_w09.databinding.ActivityMainBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    val progress = intent.getIntExtra("PROGRESS", 0)
                    binding.mainPRGDownload.progress = progress
                }
            }

        }

        binding.mainFABRun.setOnClickListener {
            startSoundService()
        }
        binding.mainBTNNext.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        val intentFilter = IntentFilter("100_FM")
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter)



    }

    private fun startSoundService() {
        Thread(Runnable {
            var x = 0.0
            var y = 0.0
            for (i in 0..9) {
                Log.d("pttt", "" + i)
                for (j in 0..3*9999999) {
                    x = x.pow((j % 3).toDouble())
                    y = x
                }

                updateTextUi()


            }
        }).start()

        val intent = Intent(this, MyService::class.java)
        intent.setAction("RUN")
        startService(intent)
    }

    private fun updateTextUi() {
        Log.d("pttt A", Thread.currentThread().name)

        runOnUiThread(Runnable {
            Log.d("pttt B", Thread.currentThread().name)
            binding.textView.text = "hhih"
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }
}