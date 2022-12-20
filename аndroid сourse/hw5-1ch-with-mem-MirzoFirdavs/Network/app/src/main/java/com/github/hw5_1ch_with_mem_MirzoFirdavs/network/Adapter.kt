package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.DateFormat

class Adapter(private val messages: List<Message>, private val images: Map<Long, Bitmap>) :
    RecyclerView.Adapter<Adapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val holder = MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
        holder.image.setOnClickListener {
            parent.context.startActivity(
                Intent(parent.context, FullscreenActivity::class.java).putExtra(
                    "imageLink",
                    messages[holder.bindingAdapterPosition].imageLink
                )
            )
        }
        return holder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (id, senderName, _, sendTime, text) = messages[position]
        holder.senderName.text = senderName
        holder.messageText.text = text
        holder.sendTime.text = DateFormat.getDateTimeInstance().format(sendTime).toString()
        holder.image.setImageBitmap(images[id])
    }

    override fun getItemCount(): Int = messages.size

    class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.tv_message_sender_name)
        val messageText: TextView = itemView.findViewById(R.id.tv_message_text)
        val image: ImageView = itemView.findViewById(R.id.ib_message_image)
        val sendTime: TextView = itemView.findViewById(R.id.tv_message_send_time)
    }
}