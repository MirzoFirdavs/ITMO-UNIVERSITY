package com.github.mirzofirdavs.messenger

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class ImageActivity : AppCompatActivity() {
    private lateinit var picture: ImageView
    private val instance = MessengerApplication.instance
    private val prettyImagesCache = ConcurrentHashMap<String, Bitmap>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        picture = findViewById(R.id.picturePretty)
        val link = intent.getStringExtra("link")!!
        lifecycleScope.launch(Dispatchers.IO) {
            val bitmap = prettyImagesCache.getOrPut(link) {
                downloadPrettyImage(link)
            }

            withContext(Dispatchers.Main) {
                picture.setImageBitmap(bitmap)
            }
        }
    }

    private suspend fun downloadPrettyImage(link: String): Bitmap =
        BitmapFactory.decodeStream(instance.service.downloadPrettyImage(link).body()!!.byteStream())
}