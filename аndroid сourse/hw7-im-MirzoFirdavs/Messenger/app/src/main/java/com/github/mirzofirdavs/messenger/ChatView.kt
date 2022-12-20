package com.github.mirzofirdavs.messenger

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatView(root: View) : RecyclerView.ViewHolder(root) {
    val chatView: TextView = root.findViewById(R.id.chat)

    fun bind(chat: String) {
        chatView.text = chat
    }
}