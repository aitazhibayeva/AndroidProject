package com.example.moneytrack.views.activities.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moneytrack.databinding.ActivityProfileBinding
import com.example.moneytrack.views.activities.MainActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            goBack()
        }

        binding.editProfile.setOnClickListener {
            val userUsername = binding.profileUsername.text.toString().trim { it <= ' ' }
            viewModel.fetchUserData(userUsername)
            passUserData()
        }

        viewModel.name.observe(this, Observer { name ->
            binding.profileName.text = name
            binding.titleName.text = name
        })

        viewModel.email.observe(this, Observer { email ->
            binding.profileEmail.text = email
        })

        viewModel.username.observe(this) { username ->
            binding.profileUsername.text = username
            binding.titleUsername.text = username
        }

        viewModel.password.observe(this) { password ->
            binding.profilePassword.text = password
        }

        showUserData()
    }

    private fun showUserData() {
        val intent = intent
        val nameUser = intent.getStringExtra("name")
        val emailUser = intent.getStringExtra("email")
        val usernameUser = intent.getStringExtra("username")
        val passwordUser = intent.getStringExtra("password")

        viewModel.loadUserData(nameUser, emailUser, usernameUser, passwordUser)
    }

    private fun passUserData() {
        val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
        intent.putExtra("name", viewModel.name.value)
        intent.putExtra("email", viewModel.email.value)
        intent.putExtra("username", viewModel.username.value)
        intent.putExtra("password", viewModel.password.value)
        startActivity(intent)
    }

    private fun goBack() {
        val intent = Intent(this@ProfileActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
