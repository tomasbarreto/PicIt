package com.example.picit.notifications

import androidx.lifecycle.ViewModel
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RoomInviteNotificationsViewModel: ViewModel() {

    fun removeJoinRoomRequests(user: User, request: JoinRoomRequest) {
        var db = Firebase.database
        val userRef = db.getReference("users/${user.id}")
        val updatedRoomRequests = user.requestsToJoin.toMutableList()
        updatedRoomRequests.remove(request)
        val updatedUser = user.copy(requestsToJoin = updatedRoomRequests)
        userRef.setValue(updatedUser)
    }
}