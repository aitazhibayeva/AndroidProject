package com.example.moneytrack.views.activities.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moneytrack.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showData()

        viewModel.name.observe(this, Observer { name ->
            binding.editName.setText(name)
        })

        viewModel.email.observe(this, Observer { email ->
            binding.editEmail.setText(email)
        })

        viewModel.username.observe(this, Observer { username ->
            binding.editUsername.setText(username)
        })

        viewModel.password.observe(this, Observer { password ->
            binding.editPassword.setText(password)
        })

        viewModel.toastMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        binding.saveButton.setOnClickListener {
            val newName = binding.editName.text.toString()
            val newEmail = binding.editEmail.text.toString()
            val newPassword = binding.editPassword.text.toString()
            viewModel.saveData(newName, newEmail, newPassword)
        }
    }

    private fun showData() {
        val intent = intent
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        viewModel.loadData(name, email, username, password)
    }
}
