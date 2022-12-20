package com.github.mirzofirdavs.messenger

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MessageRequestService {
    @GET("/thumb/{link}")
    suspend fun downloadThumbImage(@Path("link") link: String): Response<ResponseBody>

    @GET("/img/{link}")
    suspend fun downloadPrettyImage(@Path("link") link: String): Response<ResponseBody>

    @POST("/1ch")
    suspend fun sendText(@Body messageDto: MessageDto): Response<Long>

    @Multipart
    @POST("/1ch")
    suspend fun sendPicture(@Part part: MultipartBody.Part, @Part("picture") picture: RequestBody): Response<Long>

    @GET("/channels")
    suspend fun getAllChats(): Response<List<String>>

    @GET("/channel/{chat}")
    suspend fun getChatMessages(
        @Path("chat") chat: String,
        @Query("lastKnownId") lastKnownId: Int
    ): Response<List<MessageDto>>

    @GET("/inbox/{username}")
    suspend fun getUserMessages(
        @Path("username") username: String,
        @Query("lastKnownId") lastKnownId: Int,
        @Query("limit") limit: Int = 1000
    ): Response<List<MessageDto>>
}