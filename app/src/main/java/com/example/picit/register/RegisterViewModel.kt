package com.example.picit.register

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

private lateinit var auth: FirebaseAuth

class RegisterViewModel: ViewModel() {
    fun registerAccount(
        email: String, password: String,
        context: Context,
        onClickGoBackToLogin: () -> Unit={},
        username: String,
    ) {
        auth = Firebase.auth

        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")

                        val user = auth.currentUser
                        if (user != null) {
                            registerUser(user.uid, username)
                        }
                        onClickGoBackToLogin()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Registration failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun registerUser(
        userID: String,
        username: String,
    ) {
        val db = Firebase.database
        val usersRef = db.getReference("users/$userID")

        val user = User(id=userID, username=username)
        usersRef.setValue(user)
    }
}