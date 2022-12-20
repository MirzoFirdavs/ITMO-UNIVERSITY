package com.github.mirzofirdavs.hw6

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "sender_name")
    val senderName: String,

    @ColumnInfo(name = "receiver_name")
    val receiverName: String,

    @ColumnInfo(name = "send_time")
    val sendTime: Long,

    @ColumnInfo(name = "text")
    val text: String?,

    @ColumnInfo(name = "image_link")
    val imageLink: String?
)

fun Message.toMessageJson(): ReceivedMessageJson {
    return ReceivedMessageJson(id, senderName, receiverName, Data(Text(text), Image(imageLink)), sendTime)
}