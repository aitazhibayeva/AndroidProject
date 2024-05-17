package com.example.moneytrack.views.activities.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class ProfileViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun loadUserData(name: String?, email: String?, username: String?, password: String?) {
        _name.value = name ?: ""
        _email.value = email ?: ""
        _username.value = username ?: ""
        _password.value = password ?: ""
    }

    fun fetchUserData(username: String) {
        val checkUserDatabase = reference.orderByChild("username").equalTo(username)
        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _name.value = snapshot.child(username).child("name").getValue(String::class.java)
                    _email.value = snapshot.child(username).child("email").getValue(String::class.java)
                    _username.value = snapshot.child(username).child("username").getValue(String::class.java)
                    _password.value = snapshot.child(username).child("password").getValue(String::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
