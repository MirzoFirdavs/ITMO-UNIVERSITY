package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Binder
import android.util.Log
import androidx.room.Room
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.Executors

class MyService : Service() {
    private val hostAddress = "http://213.189.221.170:8008"
    private val defaultSenderName = "MirzoFirdavs"
    private val defaultReceiverName = "1@channel"
    private var lastKnownId: Long = 0

    private val bigImages = HashMap<String, Bitmap>()

    private lateinit var db: AppDatabase
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var messages: ArrayList<Message>
    private val images = HashMap<Long, Bitmap>()
    private var cacheNotLoaded = true

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "messages").build()
    }

    private fun loadCache() {
        messages = ArrayList(db.getMessageDao().getAll())
        messages.forEach {
            if (it.imageLink != null) {
                var image = loadImageFromCache(it.id)
                if (image == null) {
                    image = downloadImage(it.imageLink)
                    if (image != null) saveImageToCache(it.id, image)
                }

                if (image != null) {
                    images[it.id] = image
                }
            }
        }
        if (messages.isNotEmpty()) {
            lastKnownId = messages.last().id
        }
        cacheNotLoaded = false
    }

    fun loadMessages(loadingFinished: (ArrayList<Message>, Map<Long, Bitmap>) -> Unit) {
        executor.submit {
            if (cacheNotLoaded) {
                loadCache()
            }
            var currentId: Long = -1
            while (currentId != lastKnownId) {
                currentId = lastKnownId
                try {
                    var response: String
                    (URL("$hostAddress/1ch?limit=100&lastKnownId=$lastKnownId").openConnection() as HttpURLConnection).run {
                        connect()
                        connectTimeout = 1000
                        readTimeout = 1000
                        response = inputStream.bufferedReader().readLines().joinToString()
                    }
                    val outerArray = JSONArray(response)

                    for (elem in 0 until outerArray.length()) {
                        Log.d("TAG", "$lastKnownId")
                        val obj = outerArray.getJSONObject(elem)
                        val senderName = obj.getString("from")
                        lastKnownId = obj.getLong("id")
                        val recieverName = obj.getString("to")
                        val sendTime = obj.getString("time")
                        val text = try {
                            obj.getJSONObject("data").getJSONObject("Text").getString("text")
                        } catch (e: JSONException) {
                            null
                        }
                        val imageLink = try {
                            obj.getJSONObject("data").getJSONObject("Image").getString("link")
                        } catch (e: JSONException) {
                            null
                        }
                        messages.add(
                            Message(
                                lastKnownId,
                                senderName,
                                recieverName,
                                sendTime.toLong(),
                                text,
                                imageLink
                            )
                        )
                        val image = downloadImage(imageLink)
                        if (image != null) {
                            images[lastKnownId] = image
                            saveImageToCache(lastKnownId, image)
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    Log.e("loadMessages", "Error loading messages, socket timeout: $e")
                } catch (e: IOException) {
                    Log.e("loadMessages", "IOException: $e")
                }
            }
            db.getMessageDao().insert(messages)

            loadingFinished(messages, images)
        }
    }

    fun sendMessage(messageText: String?, refresh: () -> Unit) {
        if (messageText == null || messageText.isBlank()) return

        executor.submit {
            val request = JSONObject().apply {
                put("from", defaultSenderName)
                put("to", defaultReceiverName)
                put(
                    "data",
                    JSONObject().put(
                        "Text",
                        JSONObject().put("text", messageText)
                    )
                )
                put("time", System.currentTimeMillis())
            }


            var connection: HttpURLConnection? = null
            try {
                connection = (URL("$hostAddress/1ch").openConnection() as HttpURLConnection).apply {
                    connectTimeout = 5000
                    readTimeout = 5000
                    requestMethod = "POST"
                    doOutput = true
                    doInput = true
                    setRequestProperty("Content-Type", "application/json")
                }

                OutputStreamWriter(connection.outputStream).append(request.toString()).flush()

                if (connection.responseCode != 200) {
                    Log.e("sendMessage", connection.responseMessage)
                }
            } finally {
                connection?.disconnect()
            }
            refresh()
        }
    }


    fun downloadBigImage(url: String?, updateView: (Bitmap) -> Unit) {
        if (url == null) return
        if (bigImages.containsKey(url)) {
            bigImages[url]?.let { updateView(it) }
            return
        }
        executor.submit {
            var image: Bitmap =
                BitmapFactory.decodeResource(this.resources, R.drawable.image_not_found)
            try {
                val connection =
                    (URL("$hostAddress/img/$url").openConnection() as HttpURLConnection).apply {
                        connectTimeout = 5000
                        readTimeout = 5000
                    }

                image = BitmapFactory.decodeStream(connection.inputStream)

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("loadBigImage", "Error while loading big image: $e")
            }
            bigImages[url] = image
            updateView(image)
        }
    }

    fun sendImage(uri: Uri, refresh: () -> Unit) {
        if (uri.lastPathSegment == null) return

        executor.submit {
            val boundary = MultipartTool.generateBoundary()

            val connection = (URL("$hostAddress/1ch").openConnection() as HttpURLConnection).apply {
                connectTimeout = 5000
                readTimeout = 5000
                requestMethod = "POST"
                doOutput = true
                doInput = true
                setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            }

            MultipartTool(connection.outputStream, boundary).apply {
                appendJsonField("message", """{"from": "$defaultSenderName"}""")

                val out = ByteArrayOutputStream()
                BitmapFactory.decodeStream(contentResolver.openInputStream(uri)).apply {
                    compress(Bitmap.CompressFormat.JPEG, 100, out)
                }
                val fileName = "image.png"
                appendFile(
                    "image",
                    out.toByteArray(),
                    "image/jpeg",
                    "$lastKnownId-$fileName"
                )
                close()
            }

            Log.i("uploadImage", "${connection.responseCode}: ${connection.responseMessage}")

            connection.disconnect()

            refresh()
        }
    }

    private fun downloadImage(url: String?): Bitmap? {
        if (url == null || url.isBlank()) return null

        var connection: HttpURLConnection? = null
        return try {
            connection =
                (URL("$hostAddress/thumb/$url").openConnection() as HttpURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                }

            BitmapFactory.decodeStream(connection.inputStream)
        } catch (e: Exception) {
            Log.e("loadImage", "Error while loading image: $e")
            BitmapFactory.decodeResource(this.resources, R.drawable.image_not_found)
        } finally {
            connection?.disconnect()
        }
    }

    private fun saveImageToCache(id: Long, image: Bitmap) {
        try {
            val filename = File(applicationContext.cacheDir, "$id.png")
            FileOutputStream(filename).use { out ->
                image.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: IOException) {
            Log.e("saveImagesToCache", "Failed to save image in cache: $e")
        }
    }

    private fun loadImageFromCache(id: Long): Bitmap? {
        var image: Bitmap? = null
        try {
            val filename = File(applicationContext.cacheDir, "$id.png")
            FileInputStream(filename).use { `in` ->
                image = BitmapFactory.decodeStream(`in`)
            }
        } catch (ignored: IOException) {
        }
        return image
    }

    inner class MyBinder : Binder() {
        fun getMyService() = this@MyService
    }

    override fun onBind(p0: Intent?): MyBinder = MyBinder()
}