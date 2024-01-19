package com.example.picit.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.picit.entities.User
import com.example.picit.utils.DBUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class LoginViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseDatabase
    private val TAG: String = "LoginViewModel"

    fun loginAccount(email: String, password: String, context: Context, dbUtils: DBUtils, onClickGoToMainScreen: () -> Unit, currentUserUpdate: (User) -> Unit={}) {
            auth = Firebase.auth
            db = Firebase.database

            if(email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                dbUtils.setCurrentUserListener(currentUser.uid, currentUserUpdate)
                                onClickGoToMainScreen()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
    }

//    private fun setCurrentUserListener(uid: String, currentUserUpdate: (User) -> Unit) {
//        val userRef = db.getReference("users/$uid")
//
//        userRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue<User>()
//
//                if(user != null){
//                    user.id = uid;
//                    currentUserUpdate(user)
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })
//
//    }

    fun findUserById(currentUserId: String, onUserFound: (User) -> Unit) {
        val usersRef = db.getReference("users")
        usersRef.child(currentUserId).get().addOnSuccessListener {
            val savedUser = it.getValue<User>()
            Log.d("firebase", "Got value ${savedUser}")

            if (savedUser != null) {
                onUserFound(savedUser)
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}