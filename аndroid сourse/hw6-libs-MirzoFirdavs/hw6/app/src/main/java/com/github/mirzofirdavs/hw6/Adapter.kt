package com.github.mirzofirdavs.hw6

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import java.text.DateFormat.getDateTimeInstance

class Adapter(private val messages: ArrayList<Message>) :
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
        val (_, senderName, _, sendTime, text, url) = messages[position]
        holder.senderName.text = senderName
        holder.messageText.text = text
        holder.sendTime.text = getDateTimeInstance().format(sendTime).toString()
        if (url != null) {
            Picasso.get()
                .load("${App.instance.hostAddress}/thumb/$url")
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.image_not_found)
                .into(holder.image)
        } else {
            holder.image.setImageBitmap(null)
        }
    }

    fun notifyItemInserted(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun getItemCount(): Int = messages.size


    class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.tv_message_sender_name)
        val messageText: TextView = itemView.findViewById(R.id.tv_message_text)
        val image: ImageView = itemView.findViewById(R.id.ib_message_image)
        val sendTime: TextView = itemView.findViewById(R.id.tv_message_send_time)
    }
}