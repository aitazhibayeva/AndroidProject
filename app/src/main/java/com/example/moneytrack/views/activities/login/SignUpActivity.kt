package com.example.moneytrack.views.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moneytrack.R
import com.example.moneytrack.utils.preferences.SharedPreferencesManager
import com.example.moneytrack.views.activities.profile.ProfileActivity
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity() {

    private lateinit var signupName: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupUsername: EditText
    private lateinit var signupPassword: EditText
    private lateinit var loginRedirectText: TextView
    private lateinit var signupButton: Button
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupName = findViewById(R.id.signup_name)
        signupEmail = findViewById(R.id.signup_email)
        signupUsername = findViewById(R.id.signup_username)
        signupPassword = findViewById(R.id.signup_password)
        signupButton = findViewById(R.id.signup_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)
        sharedPreferencesManager = SharedPreferencesManager(this)

        checkIfCredentialsAreValid()

        signupButton.setOnClickListener {
            val name = signupName.text.toString().trim()
            val email = signupEmail.text.toString().trim()
            val username = signupUsername.text.toString().trim()
            val password = signupPassword.text.toString().trim()

            if (validateInput(name, email, username, password)) {
                signUpViewModel.signUpUser(name, email, username, password)
            } else {
                showValidationError()
            }
        }

        loginRedirectText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpViewModel.signUpResult.observe(this, Observer { result ->
            when (result) {
                is SignUpResult.Success -> {
                    Toast.makeText(this, "You have signed up successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                is SignUpResult.CredentialsValid -> {
                    val user = result.user
                    val intent = Intent(this@SignUpActivity, ProfileActivity::class.java)
                    intent.putExtra("name", user.name)
                    intent.putExtra("email", user.email)
                    intent.putExtra("username", user.username)
                    intent.putExtra("password", user.password)
                    startActivity(intent)
                }
                is SignUpResult.Error -> {
                    Snackbar.make(findViewById(R.id.signup_layout), result.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun validateInput(name: String, email: String, username: String, password: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
    }

    private fun showValidationError() {
        val message = "All fields are required."
        Snackbar.make(findViewById(R.id.signup_layout), message, Snackbar.LENGTH_LONG).show()
    }

    private fun checkIfCredentialsAreValid() {
        val login = sharedPreferencesManager.login ?: return
        val password = sharedPreferencesManager.password ?: return
        signUpViewModel.checkIfCredentialsAreValid(login, password)
    }
}
