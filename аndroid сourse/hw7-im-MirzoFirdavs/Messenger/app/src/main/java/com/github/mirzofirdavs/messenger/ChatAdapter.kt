package com.github.mirzofirdavs.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val chats: List<String>, private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ChatView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatView =
        ChatView(
            LayoutInflater.from(parent.context).inflate(R.layout.chat, parent, false)
        ).also { holder ->
            holder.chatView.setOnClickListener {
                onClick(chats[holder.adapterPosition])
            }
        }

    override fun onBindViewHolder(holder: ChatView, position: Int) =
        holder.bind(chats[position])

    override fun getItemCount(): Int = chats.size
}