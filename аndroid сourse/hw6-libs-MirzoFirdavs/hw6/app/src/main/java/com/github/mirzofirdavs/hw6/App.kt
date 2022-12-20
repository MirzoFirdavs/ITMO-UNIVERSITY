package com.github.mirzofirdavs.hw6

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var messageRepository: MessageRepository
        private set

    val hostAddress = "http://213.189.221.170:8008"

    override fun onCreate() {
        super.onCreate()
        instance = this

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(hostAddress)
            .build()

        messageRepository = retrofit.create(MessageRepository::class.java)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}