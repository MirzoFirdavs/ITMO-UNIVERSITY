package com.github.hw5_1ch_with_mem_MirzoFirdavs.network

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hw5_1ch_with_mem_MirzoFirdavs.network.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageSelector: ActivityResultLauncher<Intent>
    private val handler = Handler(Looper.getMainLooper())
    private val readExtStorageRequestId: Int = 0

    var myService: MyService? = null
    var loadingInProgress: Boolean = false
    var isBound = false
    private var readExternalStoragePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                readExtStorageRequestId
            )
        } else {
            readExternalStoragePermissionGranted = true
        }

        imageSelector =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val uri = it.data?.data
                    if (uri != null) {
                        val weakThis = WeakReference(this)
                        myService?.sendImage(uri) {
                            Log.d("refresh", "sendImage: $uri")
                            weakThis.get()?.refresh()
                        }
                    }
                }
            }

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
            myService?.sendMessage(binding.etNewMessage.text.toString()) {
                Log.d("refresh", "sendMessage")
                weakThis.get()?.refresh()
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
            loadWithProgress {
                val weakThis = WeakReference(this)
                myService?.loadMessages { messages, images ->
                    val activity = weakThis.get()
                    activity?.handler?.post {
                        activity.binding.rvMain.adapter = Adapter(messages, images)
                        activity.binding.rvMain.scrollToPosition(messages.size - 1)
                        activity.binding.indeterminateBar.visibility = View.GONE
                        activity.loadingInProgress = false
                    }
                }
            }
        }
    }

    private fun loadWithProgress(block: () -> Unit) {
        handler.post {
            binding.indeterminateBar.visibility = View.VISIBLE
        }
        loadingInProgress = true
        block()
    }

    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("MyService", "onServiceConnected")
            val binderBridge: MyService.MyBinder = service as MyService.MyBinder
            myService = binderBridge.getMyService()
            isBound = true
            if (!loadingInProgress) {
                loadWithProgress {
                    val weakThis = WeakReference(this@MainActivity)
                    myService?.loadMessages { messages, images ->
                        val activity = weakThis.get()
                        activity?.handler?.post {
                            activity.binding.rvMain.adapter = Adapter(messages, images)
                            activity.binding.rvMain.scrollToPosition(messages.size - 1)
                            activity.binding.indeterminateBar.visibility = View.GONE
                            activity.loadingInProgress = false
                        }
                    }
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