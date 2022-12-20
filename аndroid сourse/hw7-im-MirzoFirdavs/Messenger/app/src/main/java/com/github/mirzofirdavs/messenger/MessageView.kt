package com.github.mirzofirdavs.messenger

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Either

class MessageView(root: View) : RecyclerView.ViewHolder(root) {
    private val timeTextView: TextView = root.findViewById(R.id.time)
    private val senderTextView: TextView = root.findViewById(R.id.sender)
    private val contentTextView: TextView = root.findViewById(R.id.content)
    val pictureImageView: ImageView = root.findViewById(R.id.picture)

    fun bind(message: Message) =
        with(message) {
            timeTextView.text = time.toString()
            senderTextView.text = sender
            when (data) {
                is Either.Left -> contentTextView.text = data.value
                is Either.Right -> {
                    pictureImageView.setImageBitmap(data.value.second)
                }
            }
        }
}
