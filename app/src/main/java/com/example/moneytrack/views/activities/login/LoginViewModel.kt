package com.example.moneytrack.views.activities.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneytrack.models.User
import com.google.firebase.database.*

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

    fun checkUser(userUsername: String, userPassword: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase: Query = reference.orderByChild("username").equalTo(userUsername)

        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val passwordFromDB = snapshot.child(userUsername).child("password").getValue(String::class.java)
                    if (passwordFromDB == userPassword) {
                        Log.e("TAG", "from login: $snapshot")
                        val nameFromDB = snapshot.child(userUsername).child("name").getValue(String::class.java)
                        val emailFromDB = snapshot.child(userUsername).child("email").getValue(String::class.java)
                        val usernameFromDB = snapshot.child(userUsername).child("username").getValue(String::class.java)
                        val user = User(nameFromDB, emailFromDB, usernameFromDB, passwordFromDB)
                        _loginResult.postValue(LoginResult.Success(user))
                    } else {
                        _loginResult.postValue(LoginResult.Error("Invalid Credentials"))
                    }
                } else {
                    _loginResult.postValue(LoginResult.Error("User does not exist"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _loginResult.postValue(LoginResult.Error("Database Error: ${error.message}"))
            }
        })
    }
}

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
