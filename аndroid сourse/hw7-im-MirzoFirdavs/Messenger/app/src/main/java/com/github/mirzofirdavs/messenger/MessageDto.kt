package com.github.mirzofirdavs.messenger

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import arrow.core.Either
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(Include.NON_NULL)
class MessageDto {
    var id: Long? = null
    var from: String? = null
    @JsonIgnore
    var to: String? = null
    var data: Data? = null
    var time: Long? = null

    override fun toString(): String {
        return "id: '$id', from: '$from', to: '$to', data: '$data', time: '$time'"
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun MessageDto.toMessage(bitmap: Bitmap?) =
            Message(
                from!!,
                Instant.ofEpochSecond(time!!),
                if (data!!.text != null) {
                    Either.Left(data!!.text!!.text!!)
                } else {
                    Either.Right(
                        Pair(
                            data!!.image!!.link!!,
                            bitmap!!
                        )
                    )
                }
            )
    }
}

@JsonInclude(Include.NON_NULL)
class Data {
    @JsonProperty("Text")
    var text: Text? = null

    @JsonProperty("Image")
    var image: Image? = null

    override fun toString(): String {
        return "text: '$text', image: '$image'"
    }
}

class Text {
    var text: String? = null

    override fun toString(): String {
        return text ?: "null"
    }
}

class Image {
    var link: String? = null

    override fun toString(): String {
        return link ?: "null"
    }
}
