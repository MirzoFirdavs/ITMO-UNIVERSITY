package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var error: TextView
    private lateinit var login: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var showPassword: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        error = findViewById(R.id.display_error)
        login = findViewById(R.id.btn_login)
        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        showPassword = findViewById(R.id.btn_show_password)

        showPassword.setOnClickListener {
            if (password.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                showPassword.setBackgroundResource(R.drawable.eye)
            } else {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showPassword.setBackgroundResource(R.drawable.eye_off)
            }
            password.setSelection(password.length())
        }

        login.setOnClickListener {
            if (email.text.isBlank() && password.text.isBlank()) {
                error.text = resources.getString(R.string.error_email_and_password)
            }
            else if (email.text.isBlank()) {
                error.text = resources.getString(R.string.error_email)
            } else if (password.text.isBlank()) {
                error.text = resources.getString(R.string.error_password)
            } else {
                error.text = resources.getString(R.string.incorrect_email_or_password)
            }
        }
    }
}