package com.github.mirzofirdavs.messenger

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), FragmentChat.SelectedChatListener {
    private val messageActivityStarter: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i(LOG_TAG, "Exited single chat, listing all chats")
            startChatTransaction()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startChatTransaction()
    }

    private fun startChatTransaction() {
        Log.i(LOG_TAG, "Starting chat transaction")
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_chat_container, FragmentChat.newInstance(), CHAT_FRAGMENT_TAG)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSelectedChat(chat: String) {
        runCatching {
            val messageFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_message_container) as MessageFragment?
            messageFragment?.run {
                if (messageFragment.isVisible) {
                    setChat(chat)
                } else {
                    startMessageActivity(chat)
                }
            }
                ?: run {
                    startMessageActivity(chat)
                }
        }.onFailure {
            Log.w(LOG_TAG, it)
        }
    }

    private fun startMessageActivity(chat: String) =
        messageActivityStarter.launch(
            Intent(this, MessageActivity::class.java).apply {
                putExtra("chat", chat)
            }
        )

    companion object {
        private const val LOG_TAG = "MAIN_ACTIVITY"
        private const val CHAT_FRAGMENT_TAG = R.string.fragment_chat_container.toString()
    }
}