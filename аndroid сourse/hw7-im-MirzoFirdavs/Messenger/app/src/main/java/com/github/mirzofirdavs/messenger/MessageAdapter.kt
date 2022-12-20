package com.github.mirzofirdavs.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    var messages: List<Message>,
    private val onClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageView =
        MessageView(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.message, parent, false)
        ).also { holder ->
            holder.pictureImageView.setOnClickListener {
                onClick(messages[holder.adapterPosition])
            }
        }

    override fun onBindViewHolder(holder: MessageView, position: Int) =
        holder.bind(messages[position])

    override fun getItemCount(): Int = messages.size
}
