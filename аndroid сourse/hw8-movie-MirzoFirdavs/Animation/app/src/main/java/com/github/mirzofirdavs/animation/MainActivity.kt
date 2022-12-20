package com.github.mirzofirdavs.animation

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import com.github.mirzofirdavs.animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var animator: ObjectAnimator
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", animator.currentPlayTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        animator.currentPlayTime = savedInstanceState.getLong("key")
        animator.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animator = ObjectAnimator.ofFloat(binding.earth, View.ROTATION, 0f, -360f)
        animator.apply {
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            duration = 5000L
            animator.start()
        }
    }
}