package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

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
