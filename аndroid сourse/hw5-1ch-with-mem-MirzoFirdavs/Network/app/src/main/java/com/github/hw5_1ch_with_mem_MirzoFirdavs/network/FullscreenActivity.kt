package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.hw5_1ch_with_mem_MirzoFirdavs.network.databinding.ActivityImageBinding
import java.lang.ref.WeakReference

class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding

    private val handler = Handler(Looper.getMainLooper())

    var myService: MyService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MyService::class.java)
        startService(intent)
        bindService(intent, boundServiceConnection, BIND_AUTO_CREATE)

        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibImage.setOnClickListener { this.finish() }
    }

    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("MyService", "onServiceConnected")
            val binderBridge: MyService.MyBinder = service as MyService.MyBinder
            myService = binderBridge.getMyService()
            isBound = true
            loadImage()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MyService", "onServiceDisconnected")
            myService = null
            isBound = false
        }
    }

    fun loadImage() {
        val url: String? = intent.getStringExtra("imageLink")

        try {
            val weakThis = WeakReference(this@FullscreenActivity)
            myService?.downloadBigImage(url) {
                val activity = weakThis.get()
                activity?.handler?.post {
                    val width = activity.window.decorView.width
                    val height = (it.height * width) / it.width
                    activity.binding.ibImage.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            it,
                            width,
                            height,
                            false
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@FullscreenActivity,
                "Something went wrong",
                Toast.LENGTH_LONG
            ).show()
            Log.e("loadImage", "Couldn't load bid image: $e")
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(boundServiceConnection)
        }
    }
}