package com.example.moneytrack.views.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moneytrack.R
import com.example.moneytrack.utils.preferences.SharedPreferencesManager
import com.example.moneytrack.views.activities.profile.ProfileActivity
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {

    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferencesManager = SharedPreferencesManager(this)

        loginUsername = findViewById(R.id.login_username)
        loginPassword = findViewById(R.id.login_password)
        signupRedirectText = findViewById(R.id.signupRedirectText)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            if (!validateUsername() || !validatePassword()) {
                Snackbar.make(findViewById(R.id.login_layout), "Invalid format", Snackbar.LENGTH_LONG).show()
            } else {
                val username = loginUsername.text.toString().trim()
                val password = loginPassword.text.toString().trim()
                loginViewModel.checkUser(username, password)
            }
        }

        signupRedirectText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is LoginResult.Success -> {
                    val user = result.user
                    sharedPreferencesManager.saveLoginDetails(user.username, user.password)
                    val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                    intent.putExtra("name", user.name)
                    intent.putExtra("email", user.email)
                    intent.putExtra("username", user.username)
                    intent.putExtra("password", user.password)
                    startActivity(intent)
                }
                is LoginResult.Error -> {
                    if (result.message == "Invalid Credentials") {
                        loginPassword.error = result.message
                        loginPassword.requestFocus()
                    } else {
                        loginUsername.error = result.message
                        loginUsername.requestFocus()
                    }
                }
            }
        })
    }

    private fun validateUsername(): Boolean {
        val `val` = loginUsername.text.toString()
        return if (`val`.isEmpty()) {
            loginUsername.error = "Username cannot be empty"
            false
        } else {
            loginUsername.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val `val` = loginPassword.text.toString()
        return if (`val`.isEmpty()) {
            loginPassword.error = "Password cannot be empty"
            false
        } else {
            loginPassword.error = null
            true
        }
    }
}
