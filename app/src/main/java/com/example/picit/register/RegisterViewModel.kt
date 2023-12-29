package com.example.picit.register

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.picit.entities.JoinRoomRequest
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
        name: String
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
                            registerUser(user.uid, username, name)
                        }
                        onClickGoBackToLogin()
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

    private fun registerUser(
        userID: String,
        username: String,
        name: String
    ) {
        val database = Firebase.database

        val usernameRef = database.getReference("users/" + userID + "/username")
        val nameRef = database.getReference("users/" + userID + "/name")
        val maxPointsRef = database.getReference("users/" + userID + "/maxPoints")
        val totalWinsRef = database.getReference("users/" + userID + "/totalWins")
        val maxWinStreakRef = database.getReference("users/" + userID + "/maxWinStreak")
        val nrPhotosTakenRef = database.getReference("users/" + userID + "/nrPhotosTaken")
        val roomsRef = database.getReference("users/" + userID + "/rooms")
        val friendsRef = database.getReference("users/" + userID + "/friends")
        val requestsToJoinRef = database.getReference("users/" + userID + "/requestsToJoin")
        val friendRequestsRef = database.getReference("users/" + userID + "/friendRequests")

        usernameRef.setValue(username)
        nameRef.setValue(name)
        maxPointsRef.setValue(0)
        totalWinsRef.setValue(0)
        maxWinStreakRef.setValue(0)
        nrPhotosTakenRef.setValue(0)
        roomsRef.setValue(emptyList<String>())
        friendsRef.setValue(emptyList<Int>())
        requestsToJoinRef.setValue(emptyList<JoinRoomRequest>())
        friendRequestsRef.setValue(emptyList<Int>())
    }
}