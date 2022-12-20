package com.github.mirzofirdavs.hw6

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Binder
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.*
import java.net.SocketTimeoutException


class MyService : Service() {
    private val defaultSenderName = "Mifik"
    private val defaultReceiverName = "1@channel"
    private var lastKnownId: Long = 0

    private lateinit var db: AppDatabase

    private lateinit var messages: ArrayList<Message>
    private var cacheNotLoaded = true

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "messages").build()
        messages = ArrayList()
    }

    private fun loadCache() {
        messages = ArrayList(db.getMessageDao().getAll())
        if (messages.isNotEmpty()) {
            lastKnownId = messages.last().id
        }
        Log.d("loadCache", "Loaded $lastKnownId messages from cache")
        cacheNotLoaded = false
    }

    suspend fun loadMessages(
        updateView: (ArrayList<Message>) -> Unit,
        loadingFinished: () -> Unit,
        onError: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        if (cacheNotLoaded) {
            loadCache()
            updateView(messages)
        }
        var currentId: Long = -1
        val messagesToStore = ArrayList<Message>()
        var response: Response<List<ReceivedMessageJson>>? = null
        while (currentId != lastKnownId) {
            currentId = lastKnownId
            try {
                val newMessages = ArrayList<Message>()
                response =
                    App.instance.messageRepository.getMessages(100, lastKnownId, false)

                response.body()?.forEach {
                    newMessages.add(it.toMessage())
                    lastKnownId = it.id
                }
                if (newMessages.isNotEmpty()) {
                    messages.addAll(newMessages)
                    updateView(messages)
                    messagesToStore.addAll(newMessages)
                }
            } catch (e: SocketTimeoutException) {
                Log.e("loadMessages", "Error loading messages, socket timeout: $e")
            } catch (e: IOException) {
                Log.e("loadMessages", "Error loading messages: $e")
            }
        }
        if (messagesToStore.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                db.getMessageDao().insert(messagesToStore)
            }
        }
        if (response != null && !response.isSuccessful) {
            onError(getMessageFromCode(response.code()))
        }
        updateView(messages)

        loadingFinished()
    }

    suspend fun sendMessage(messageText: String?, refresh: () -> Unit, onError: (String) -> Unit) =
        withContext(Dispatchers.IO) {
            if (messageText == null || messageText.isBlank()) return@withContext

            val message = SentMessageJson(
                defaultSenderName,
                defaultReceiverName,
                Data(Text(messageText)),
                System.currentTimeMillis()
            )

            var response: Response<Int>? = null
            try {
                response = App.instance.messageRepository.sendMessage(message)
            } catch (e: SocketTimeoutException) {
                Log.e("sendMessage", "Error loading messages, socket timeout: $e")
            } catch (e: IOException) {
                Log.e("sendMessage", "Error loading messages: $e")
            }

            if (response != null && !response.isSuccessful) {
                onError(getMessageFromCode(response.code()))
            } else {
                refresh()
            }
        }

    private fun convertBitmapToFile(uri: Uri): File {
        val fileName = "$lastKnownId-image.jpeg"
        val file = File(applicationContext.cacheDir, fileName)
        file.createNewFile()

        val out = ByteArrayOutputStream()
        BitmapFactory.decodeStream(contentResolver.openInputStream(uri)).apply {
            compress(Bitmap.CompressFormat.JPEG, 100, out)
        }

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(out.toByteArray())
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    suspend fun sendImage(uri: Uri, refresh: () -> Unit, onError: (String) -> Unit) {
        if (uri.lastPathSegment == null) return
        val fileName = "$lastKnownId-image.jpeg"
        val message = SentImageJson(
            defaultSenderName,
            defaultReceiverName,
            System.currentTimeMillis()
        )

        val file = convertBitmapToFile(uri)
        val image = MultipartBody.Part.createFormData(
            "image",
            fileName,
            file.asRequestBody(contentType = "image/jpeg".toMediaTypeOrNull())
        )
        var response: Response<Int>? = null
        try {
            response = App.instance.messageRepository.sendImage(message, image)
        } catch (e: SocketTimeoutException) {
            Log.e("sendImage", "Error sending image, socket timeout: $e")
        } catch (e: IOException) {
            Log.e("sendImage", "Error sending image: $e")
        }

        if (response != null && !response.isSuccessful) {
            onError(getMessageFromCode(response.code()))
        } else {
            refresh()
        }
    }

    private fun getMessageFromCode(code: Int): String {
        return "${code}: " + when (code) {
            404 -> resources.getString(R.string.response_code_404_message)
            409 -> resources.getString(R.string.response_code_409_message)
            413 -> resources.getString(R.string.response_code_413_message)
            in 500..599 -> resources.getString(R.string.response_code_5xx_message)
            else -> resources.getString(R.string.response_code_unknown_message)
        }
    }

    inner class MyBinder : Binder() {
        fun getMyService() = this@MyService
    }

    override fun onBind(p0: Intent?): MyBinder = MyBinder()
}