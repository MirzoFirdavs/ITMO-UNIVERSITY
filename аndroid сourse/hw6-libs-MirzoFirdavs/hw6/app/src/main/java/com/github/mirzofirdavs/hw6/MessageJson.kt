package com.github.mirzofirdavs.hw6

import com.squareup.moshi.Json

data class ReceivedMessageJson(
    @property:Json(name = "id")
    val id: Long,

    @property:Json(name = "from")
    val senderName: String,

    @property:Json(name = "to")
    val receiverName: String,

    @Json(name = "data")
    val data: Data,

    @Json(name = "time")
    val sendTime: Long
)

data class SentMessageJson(
    @property:Json(name = "from")
    val senderName: String,

    @property:Json(name = "to")
    val receiverName: String,

    @Json(name = "data")
    val data: Data,

    @Json(name = "time")
    val sendTime: Long
)

data class SentImageJson(
    @property:Json(name = "from")
    val senderName: String,

    @property:Json(name = "to")
    val receiverName: String,

    @Json(name = "time")
    val sendTime: Long
)

data class Data(
    @Json(name = "Text")
    val text: Text? = null,

    @Json(name = "Image")
    val image: Image? = null
)

data class Text(
    @Json(name = "text")
    val text: String? = null
)

data class Image(
    @Json(name = "link")
    val imageLink: String? = null
)

fun ReceivedMessageJson.toMessage(): Message {
    return Message(id, senderName, receiverName, sendTime, data.text?.text, data.image?.imageLink)
}
