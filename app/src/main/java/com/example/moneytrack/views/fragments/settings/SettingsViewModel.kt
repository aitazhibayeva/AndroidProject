package com.example.moneytrack.views.fragments.settings

import com.example.moneytrack.models.User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel : ViewModel() {

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun fetchUserData(login: String, password: String) {
        databaseReference.child(login).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val username = snapshot.child("username").getValue(String::class.java)
                    val passwordFromDb = snapshot.child("password").getValue(String::class.java)

                    if(username == login && password == passwordFromDb){
                        val name = snapshot.child("name").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)

                        val user = User(name, email, username, passwordFromDb)
                        _userData.postValue(user)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }
}

