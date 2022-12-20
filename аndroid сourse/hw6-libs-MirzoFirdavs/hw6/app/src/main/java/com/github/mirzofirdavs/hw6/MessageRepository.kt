package com.github.mirzofirdavs.hw6

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MessageRepository {

    @GET("/1ch")
    suspend fun getMessages(
        @Query("limit") limit: Long,
        @Query("lastKnownId") lastKnownId: Long,
        @Query("reverse") reverse: Boolean
    ): Response<List<ReceivedMessageJson>>

    @POST("/1ch")
    suspend fun sendMessage(
        @Body post: SentMessageJson
    ): Response<Int>

    @Multipart
    @POST("/1ch")
    suspend fun sendImage(
        @Part("message") message: SentImageJson,
        @Part image: MultipartBody.Part
    ): Response<Int>
}