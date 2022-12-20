package com.github.mirzofirdavs.messenger

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MessengerApplication : Application() {
    private lateinit var retrofit: Retrofit
    lateinit var service: MessageRequestService

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofit = Retrofit.Builder()
            .baseUrl("http://213.189.221.170:8008")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        service = retrofit.create(MessageRequestService::class.java)
    }

    companion object {
        lateinit var instance: MessengerApplication
            private set
    }
}