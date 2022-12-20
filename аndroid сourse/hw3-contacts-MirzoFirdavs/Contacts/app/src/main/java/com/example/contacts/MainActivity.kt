package com.example.contacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val myRequestIdContacts = 100
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        fillContacts()
    }

    private fun fillContacts(): Int {
        val contacts = mutableListOf<Contact>()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                myRequestIdContacts
            )
        } else {
            loadContacts(contacts)
            println(contacts.size)
            binding.myRecyclerView.adapter = ContactAdapter(contacts, this)
        }
        return contacts.size
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            myRequestIdContacts -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val count = fillContacts()
                    Toast.makeText(
                        this,
                        resources.getQuantityString(
                            R.plurals.numberOfContactsAvailable,
                            count,
                            count
                        ),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Для работы приложения требуется дать доступ к контактам.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun loadContacts(contacts: MutableList<Contact>) {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                contacts.add(
                    Contact(
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    )
                )
            }
            cursor.close()
        }
    }
}