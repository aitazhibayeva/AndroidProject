package com.example.moneytrack.views.activities.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun loadData(name: String?, email: String?, username: String?, password: String?) {
        _name.value = name ?: ""
        _email.value = email ?: ""
        _username.value = username ?: ""
        _password.value = password ?: ""
    }

    fun saveData(newName: String, newEmail: String, newPassword: String) {
        val currentUsername = _username.value ?: return

        var isChanged = false

        if (_name.value != newName) {
            reference.child(currentUsername).child("name").setValue(newName)
            _name.value = newName
            isChanged = true
        }

        if (_email.value != newEmail) {
            reference.child(currentUsername).child("email").setValue(newEmail)
            _email.value = newEmail
            isChanged = true
        }

        if (_password.value != newPassword) {
            reference.child(currentUsername).child("password").setValue(newPassword)
            _password.value = newPassword
            isChanged = true
        }

        _toastMessage.value = if (isChanged) "Saved" else "No Changes Found"
    }
}
