package com.github.mirzofirdavs.messenger

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

class MessageActivity : FragmentActivity(), MessageFragment.SelectedMessageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
        } else {
            runCatching {
                showMessages()
            }.onFailure {
                Log.w(LOG_TAG, it)
            }
        }
    }

    override fun onMessageSelected(updated: Boolean) {
        runCatching {
            val intent = Intent()
            intent.putExtra("updated", updated)
            setResult(RESULT_FIRST_USER, intent)
            if (updated) {
                finish()
            }
        }.onFailure {
            Log.w(LOG_TAG, it)
        }
    }

    private fun showMessages() {
        val chat = intent.getStringExtra("chat")
        if (chat == null) {
            Log.w(LOG_TAG, "No chat")
            return
        }

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_message_container,
                MessageFragment.newInstance().apply {
                    arguments = Bundle().apply {
                        putString("chat", chat)
                    }
                }
            ).commit()
    }

    companion object {
        private const val LOG_TAG = "MESSAGE_ACTIVITY"
    }
}