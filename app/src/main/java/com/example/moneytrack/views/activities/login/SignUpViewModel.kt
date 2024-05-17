package com.example.moneytrack.views.activities.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneytrack.utils.HelperClass
import com.google.firebase.database.*

class SignUpViewModel : ViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> get() = _signUpResult

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.getReference("users")

    fun signUpUser(name: String, email: String, username: String, password: String) {
        val helperClass = HelperClass(name, email, username, password)
        reference.child(username).setValue(helperClass)
            .addOnSuccessListener {
                _signUpResult.postValue(SignUpResult.Success)
            }
            .addOnFailureListener { e ->
                _signUpResult.postValue(SignUpResult.Error(e.message ?: "Unknown error"))
            }
    }

    fun checkIfCredentialsAreValid(login: String, password: String) {
        reference.child(login).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val passwordFromDB = snapshot.child("password").getValue(String::class.java)
                    if (passwordFromDB == password) {
                        val nameFromDB = snapshot.child("name").getValue(String::class.java)
                        val emailFromDB = snapshot.child("email").getValue(String::class.java)
                        val usernameFromDB = snapshot.child("username").getValue(String::class.java)

                        val user = User(nameFromDB, emailFromDB, usernameFromDB, passwordFromDB)
                        _signUpResult.postValue(SignUpResult.CredentialsValid(user))
                    } else {
                        _signUpResult.postValue(SignUpResult.Error("Invalid credentials"))
                    }
                } else {
                    _signUpResult.postValue(SignUpResult.Error("User does not exist"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _signUpResult.postValue(SignUpResult.Error("Database error: ${error.message}"))
            }
        })
    }
}

data class User(val name: String?, val email: String?, val username: String?, val password: String?)
sealed class SignUpResult {
    object Success : SignUpResult()
    data class CredentialsValid(val user: User) : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}
