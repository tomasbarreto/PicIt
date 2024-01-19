package com.example.picit.friendslist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.GameType
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class FriendListAddRoomViewModel: ViewModel() {
    val friendsShownToAdd = mutableListOf<User>()
    val friendsSelectedToAdd = mutableListOf<User>()

    fun getFriendsToAdd(membersOfRoomIds: List<String>) {
        var db = Firebase.database
        val usersRef = db.getReference("users")
        usersRef.get().addOnSuccessListener { usersListSnapshot ->
            for(userSnapshtop in usersListSnapshot.children) {
                val user = userSnapshtop.getValue<User>() // doesnt have the id

                if(user != null){
                    val userId = userSnapshtop.key.toString()
                    user.id = userId
                    if (!membersOfRoomIds.contains(user.id)) {
                        friendsShownToAdd.add(user)
                    }
                }

            }
            Log.d("firebase", "ALL USERS TO ADD: $friendsShownToAdd")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun sendJoinRoomRequests(gameType: GameType, roomId: String, roomName: String, currentUsername: String) {
        var db = Firebase.database
        friendsSelectedToAdd.forEach { user ->
            val newRoomRequest = JoinRoomRequest(currentUsername, roomId, roomName, gameType)

            val updatedRoomRequests = user.requestsToJoin.toMutableList()
            updatedRoomRequests.add(newRoomRequest)
            val updatedUser = user.copy(requestsToJoin = updatedRoomRequests)

            val userRef = db.getReference("users/${user.id}")
            userRef.setValue(updatedUser)
        }
    }
}