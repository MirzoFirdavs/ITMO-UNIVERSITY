package com.github.mirzofirdavs.messenger

import android.graphics.Bitmap
import arrow.core.Either
import java.time.Instant

data class Message(
    val sender: String,
    val time: Instant,
    val data: Either<String, Pair<String, Bitmap>>
)
