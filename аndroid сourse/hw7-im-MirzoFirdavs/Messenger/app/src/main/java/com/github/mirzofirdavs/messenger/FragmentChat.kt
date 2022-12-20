package com.github.mirzofirdavs.messenger

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mirzofirdavs.messenger.databinding.ChatBinding
import com.github.mirzofirdavs.messenger.MessageDto.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

class FragmentChat(private val username: String = "mifik") : Fragment() {
    private lateinit var binding: ChatBinding
    private val service = MessengerApplication.instance.service
    private val chats: CopyOnWriteArraySet<String> = CopyOnWriteArraySet()
    private var cnt: Int = 0
    private val messages: CopyOnWriteArrayList<Message> = CopyOnWriteArrayList()
    private val cache = ConcurrentHashMap<String, Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAllChats(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAllChats(view: View) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            runCatching {
                val response = service.getAllChats()

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    chats.addAll(response.body() as List<String>)
                    Log.i(LOG_TAG, "Updated chats: '$chats'")
                } else {
                    Log.w(LOG_TAG, "Failed to get chats")
                    toastError(getString(R.string.get_chats_failure))
                }

                getUserMessages()

                withContext(Dispatchers.Main) {
                    Log.i(LOG_TAG, "Showing chats '$chats'")

                    val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_chat_view)

                    recyclerView?.layoutManager = LinearLayoutManager(view.context)
                    recyclerView?.adapter = ChatAdapter(chats.toList().filterNotNull()) { chat ->
                        Log.i("${LOG_TAG}_CLICK", "Clicked on chat '$chat'")
                        (activity as SelectedChatListener?)?.onSelectedChat(chat)
                    }
                }
            }.onFailure {
                Log.w(LOG_TAG, it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getUserMessages() {
        val response = service.getUserMessages(username, cnt)

        if (response.code() == HttpURLConnection.HTTP_OK) {
            val receivedMessages = response.body() as List<MessageDto>

            messages.addAll(
                receivedMessages.map {
                    it.toMessage(
                        it.data!!.image?.let { image ->
                            cache.getOrPut(image.link!!) {
                                downloadThumbImage(image.link!!)
                            }
                        }
                    )
                }
            )

            cnt += receivedMessages.size

            chats.addAll(
                receivedMessages.map {
                    if (it.from == username) {
                        it.to
                    } else {
                        it.from
                    }
                }
            )
        } else {
            Log.w(LOG_TAG, "Failed to get inbox messages")
            toastError(getString(R.string.get_inbox_messages_failure))
        }
    }

    private suspend fun downloadThumbImage(link: String): Bitmap =
        BitmapFactory.decodeStream(service.downloadThumbImage(link).body()!!.byteStream())


    private suspend fun toastError(message: String) =
        withContext(Dispatchers.Main) {
            Toast.makeText(
                activity,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

    interface SelectedChatListener {
        fun onSelectedChat(chat: String)
    }

    companion object {
        private const val LOG_TAG = "CHAT_FRAGMENT"

        @JvmStatic
        fun newInstance() = FragmentChat()
    }
}
