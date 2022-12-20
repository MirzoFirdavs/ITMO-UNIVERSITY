package com.github.mirzofirdavs.hw6

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager

import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mirzofirdavs.hw6.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageSelector: ActivityResultLauncher<Intent>
    private lateinit var messageAdapter: Adapter
    private val handler = Handler(Looper.getMainLooper())
    private val readExtStorageRequestId: Int = 0
    private var myService: MyService? = null

    var loadingInProgress: Boolean = false
    var isBound = false
    var readExternalStoragePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPermissions()

        imageSelector =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val uri = it.data?.data
                    if (uri != null) {
                        val weakThis = WeakReference(this)
                        lifecycle.coroutineScope.launch {
                            myService?.sendImage(uri, refresh = {
                                weakThis.get()?.refresh()
                            }, onError = { errorMessage ->
                                Toast.makeText(
                                    weakThis.get(),
                                    errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                        }
                    }
                }
            }

        messageAdapter = Adapter(ArrayList())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = messageAdapter

        binding.btnAddImage.setOnClickListener {
            if (readExternalStoragePermissionGranted) {
                addImage()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_external_storage_permission_not_granted),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnSend.setOnClickListener {
            val weakThis = WeakReference(this)
            lifecycle.coroutineScope.launch {
                val activity = weakThis.get()
                myService?.sendMessage(
                    activity?.binding?.etNewMessage?.text.toString(),
                    refresh = {
                        activity?.refresh()
                    }, onError = { errorMessage ->
                        Toast.makeText(
                            weakThis.get(),
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    })
            }
            binding.etNewMessage.text.clear()
        }

        binding.btnRefresh.setOnClickListener {
            refresh()
        }
    }

    private fun addImage() {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            Log.e("uploadImage", "no access to storage")
            return
        }
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        imageSelector.launch(intent)
    }

    private fun refresh() {
        if (!loadingInProgress) {
            val weakThis = WeakReference(this)
            loadWithProgress {
                val activity = weakThis.get()
                myService?.loadMessages(
                    updateView = {
                        activity?.handler?.post {
                            for (i in activity.messageAdapter.itemCount until it.size) {
                                activity.messageAdapter.notifyItemInserted(it[i])
                            }
                            activity.binding.rvMain.scrollToPosition(activity.messageAdapter.itemCount - 1)
                        }
                    },
                    loadingFinished = {
                        activity?.handler?.post {
                            activity.binding.indeterminateBar.visibility = View.GONE
                            activity.loadingInProgress = false
                        }
                    }, onError = { errorMessage ->
                        Toast.makeText(
                            weakThis.get(),
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    })
            }
        }
    }

    private fun loadWithProgress(block: suspend () -> Unit) {
        handler.post {
            binding.indeterminateBar.visibility = View.VISIBLE
        }
        loadingInProgress = true
        lifecycle.coroutineScope.launch {
            block()
        }
    }

    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("MyService", "onServiceConnected")
            val binderBridge: MyService.MyBinder = service as MyService.MyBinder
            myService = binderBridge.getMyService()
            isBound = true
            if (!loadingInProgress) {
                val weakThis = WeakReference(this@MainActivity)
                loadWithProgress {
                    val activity = weakThis.get()
                    myService?.loadMessages(
                        updateView = {
                            activity?.handler?.post {
                                for (i in activity.messageAdapter.itemCount until it.size) {
                                    activity.messageAdapter.notifyItemInserted(it[i])
                                }
                                activity.binding.rvMain.scrollToPosition(activity.messageAdapter.itemCount - 1)
                            }
                        },
                        loadingFinished = {
                            activity?.handler?.post {
                                activity.binding.indeterminateBar.visibility = View.GONE
                                activity.loadingInProgress = false
                            }
                        }, onError = { errorMessage ->
                            Toast.makeText(
                                weakThis.get(),
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        })
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MyService", "onServiceDisconnected")
            myService = null
            isBound = false
        }

    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, MyService::class.java)
        startService(intent)
        bindService(intent, boundServiceConnection, BIND_AUTO_CREATE)
    }

    private fun getPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MyService", "getPermissions")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    readExtStorageRequestId
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    readExtStorageRequestId
                )
            }
        } else {
            readExternalStoragePermissionGranted = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            readExtStorageRequestId -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readExternalStoragePermissionGranted = true
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(boundServiceConnection)
        }
    }
}