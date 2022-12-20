package com.github.mirzofirdavs.messenger

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mirzofirdavs.messenger.databinding.MessageBinding
import com.github.mirzofirdavs.messenger.MessageDto.Companion.toMessage
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Integer.max
import java.net.HttpURLConnection
import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList


class MessageFragment(private val username: String = "mifik") : Fragment() {
    private val service = MessengerApplication.instance.service
    private lateinit var binding: MessageBinding
    private var chat: String = "2@channel"
    private var lastKnownId: Int = 0
    private lateinit var fragmentView: View
    private lateinit var coroutineScope: CoroutineScope
    private var recyclerView: RecyclerView? = null
    private val messages: CopyOnWriteArrayList<Message> = CopyOnWriteArrayList()
    private val thumbImagesCache = ConcurrentHashMap<String, Bitmap>()
    private var adapter: MessageAdapter? = null
    private var attachedPicture: Uri? = null
    private val getPicture = registerForActivityResult(ActivityResultContracts.GetContent()) {
        attachedPicture = it
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MessageBinding.inflate(layoutInflater)
        Log.i(LOG_TAG, "Activity is $activity")

        runCatching {
            val args = arguments
            if (args != null) {
                args.getString("chat")?.let {
                    chat = it
                    Log.i(LOG_TAG, "onCreateView chat '$chat'")
                }
            }
        }.onFailure {
            Log.w(LOG_TAG, it)
        }

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        showAllMessages()
    }

    override fun onDestroy() {
        if (this::coroutineScope.isInitialized) {
            coroutineScope.cancel()
        }
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setChat(chat: String) {
        this.chat = chat
        Log.i(LOG_TAG, "Set chat '$chat'")
        recyclerView = null
        coroutineScope.cancel()
        showAllMessages()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAllMessages() {
        coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(Dispatchers.IO) {
            lastKnownId = 0
            Log.i(LOG_TAG, "Start for channel: '${chat.isChannel()}")
            if (chat.isChannel()) {
                getChannelMessages()
            } else {
                getChatMessages()
            }
        }

        val messageInput = fragmentView.findViewById<EditText>(R.id.messageInput)
        val attachButton = fragmentView.findViewById<ImageButton>(R.id.attachButton)
        val sendButton = fragmentView.findViewById<ImageButton>(R.id.sendButton)

        attachButton.setOnClickListener {
            getPicture.launch("image/*")
            coroutineScope.launch(Dispatchers.IO) {
                if (chat.isChannel()) {
                    getChannelMessages()
                } else {
                    getChatMessages()
                }
            }
        }
        sendButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val text = messageInput.text.toString()
                if (text.isBlank() && attachedPicture == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "Enter message text or attach picture",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }
                if (text.isNotBlank()) {
                    Log.i("SendText", sendText(text).toString())
                    messageInput.text.clear()
                } else {
                    runCatching {
                        Log.i("SendPicture", sendPicture().toString())
                    }.onFailure {
                        Log.w("SendPicture", it)
                    }
                    attachedPicture = null
                }
                if (chat.isChannel()) {
                    getChannelMessages(true)
                } else {
                    getChatMessages()
                }
            }
        }
    }

    private suspend fun sendText(text: String) {
        service.sendText(
            MessageDto().apply {
                from = username
                to = chat
                val dataToSend = Data()
                val dataText = Text()
                dataText.text = text
                dataToSend.text = dataText
                data = dataToSend
            }.also {
                Log.i(LOG_TAG, "Sent message is '$it'")
            }
        ).also {
            Log.i(LOG_TAG, "Sending response code is '${it.code()}'")
        }
    }

    private suspend fun sendPicture() {
        val uri = attachedPicture!!
        val bitmap =
            BitmapFactory.decodeStream(fragmentView.context.contentResolver.openInputStream(uri))
        val byteBuffer = ByteBuffer.allocate(bitmap.rowBytes * bitmap.height)
        bitmap.copyPixelsToBuffer(byteBuffer)
        val objectMapper = ObjectMapper()

        service.sendPicture(
            MultipartBody.Part.createFormData(
                "msg",
                objectMapper.writeValueAsString(MessageDto().apply {
                    from = "mifik"
                    val dataToSend = Data()
                    val dataImage = Image()
                    dataImage.link = uri.toString()
                    dataToSend.image = dataImage
                    data = dataToSend
                })
            ),
            RequestBody.create(MediaType.parse("image/jpeg"), byteBuffer.array())
        )
    }

    private fun String.isChannel(): Boolean = this.contains("@channel")

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getChannelMessages(single: Boolean = false) {
        runCatching {
            Log.i(LOG_TAG, "chat '$chat'")
            val response = service.getChatMessages(chat, lastKnownId)
            if (response.code() == HttpURLConnection.HTTP_OK) {
                val receivedMessages = (response.body() as List<MessageDto>).map {
                    lastKnownId = max(lastKnownId, (it.id ?: 0).toInt())
                    Log.i(LOG_TAG, "lastKnownId $lastKnownId")
                    it.toMessage(
                        it.data!!.image?.let { image ->
                            thumbImagesCache.getOrPut(image.link!!) {
                                runCatching {
                                    downloadThumbImage(image.link!!)
                                }.getOrElse { (thumbImagesCache.values.first()) }
                            }
                        }
                    )
                }

                messages.addAll(receivedMessages)
                Log.i(LOG_TAG, "Updated messages: '$messages'")

                updateView(receivedMessages).also {
                    if (receivedMessages.size == 20 && !single) {
                        getChannelMessages()
                    }
                }
            } else {
                Log.w(
                    LOG_TAG,
                    "Failed to get messages from chat ' $chat', http code: '${response.code()}"
                )
                toastError(getString(R.string.get_messages_from_chat_failure) + " '$chat'")
            }
        }.onFailure {
            Log.w(LOG_TAG, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getChatMessages() {
        val response = service.getUserMessages(username, lastKnownId)
        if (response.code() == HttpURLConnection.HTTP_OK) {
            val receivedMessages = response.body() as List<MessageDto>
            lastKnownId += receivedMessages.size
            val newMessages = receivedMessages.filter { it.from == chat || it.to == chat }.map {
                it.toMessage(
                    it.data!!.image?.let { image ->
                        thumbImagesCache.getOrPut(image.link!!) {
                            downloadThumbImage(image.link!!)
                        }
                    }
                )
            }
            messages.addAll(newMessages)
            updateView(newMessages)
        } else {
            Log.w(
                LOG_TAG,
                "Failed to get inbox messages for user '$username', http code: '${response.code()}"
            )
            toastError(getString(R.string.get_inbox_messages_failure))
        }
    }

    private suspend fun updateView(receivedMessages: List<Message>) {
        Log.i(LOG_TAG, "Updating view '$recyclerView'")
        withContext(Dispatchers.Main) {
            if (recyclerView == null) {
                recyclerView = fragmentView.findViewById(R.id.messages)
                recyclerView!!.layoutManager = LinearLayoutManager(fragmentView.context)
                adapter = MessageAdapter(receivedMessages) { message ->
                    Log.i("${LOG_TAG}_CLICK", "Clicked on message '$message'")
                    when (message.data) {
                        is Either.Right -> startActivity(
                            Intent(activity, ImageActivity::class.java).apply {
                                putExtra("link", message.data.value.first)
                            }
                        )
                        else -> {}
                    }
                }
                recyclerView!!.adapter = adapter
            } else {

                if (receivedMessages.size != 1) {
                    adapter?.messages = (adapter?.messages ?: mutableListOf()) + receivedMessages
                    val count = adapter?.itemCount
                    if (count != null) {
                        recyclerView!!.adapter?.notifyItemInserted(count)
                    }
                }
            }
        }
    }

    private suspend fun downloadThumbImage(link: String): Bitmap =
        BitmapFactory.decodeStream(service.downloadThumbImage(link).body()!!.byteStream())

    private suspend fun toastError(message: String) =
        runCatching {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    activity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    interface SelectedMessageListener {
        fun onMessageSelected(updated: Boolean)
    }

    companion object {
        private const val LOG_TAG = "MESSAGE_FRAGMENT"

        @JvmStatic
        fun newInstance() = MessageFragment()
    }
}