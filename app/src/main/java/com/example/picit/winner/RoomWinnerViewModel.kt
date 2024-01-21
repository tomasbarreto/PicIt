package com.example.picit.winner

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RoomWinnerViewModel: ViewModel() {

    fun removeRoomForUserRooms(room: PicDescRoom, user:User, callback: () -> Unit = {}){
        val db = Firebase.database
        // remove this room from user rooms
        val userRef = db.getReference("users/${user.id}")

        val updatedPicDescRooms = user.picDescRooms.filter { it != room.id }
        val updatedUser = user.copy(picDescRooms = updatedPicDescRooms)
        userRef.setValue(updatedUser).addOnSuccessListener {
            callback()
        }
    }

    fun removeRoomForUserRooms(room: RePicRoom, user:User, callback: () -> Unit = {}){
        val db = Firebase.database
        // remove this room from user rooms
        val userRef = db.getReference("users/${user.id}")

        val updatedRepicRooms = user.repicRooms.filter { it != room.id }
        val updatedUser = user.copy(repicRooms = updatedRepicRooms)
        userRef.setValue(updatedUser).addOnSuccessListener {
            callback()
        }
    }
}