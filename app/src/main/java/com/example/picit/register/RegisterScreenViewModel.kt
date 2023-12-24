package com.example.picit.register

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var auth: FirebaseAuth

fun registerAccount(email: String, password: String, context: Context) {
    auth = Firebase.auth

    if(email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}