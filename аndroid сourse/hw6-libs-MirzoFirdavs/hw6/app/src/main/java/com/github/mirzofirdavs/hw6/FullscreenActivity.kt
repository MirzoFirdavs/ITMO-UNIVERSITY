package com.github.mirzofirdavs.hw6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mirzofirdavs.hw6.databinding.ActivityImageBinding
import com.squareup.picasso.Picasso

class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        url = intent.getStringExtra("imageLink")

        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get()
            .load("${App.instance.hostAddress}/img/$url")
            .fit()
            .centerInside()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.image_not_found)
            .into(binding.ibImage)

        binding.ibImage.setOnClickListener { this.finish() }
    }
}