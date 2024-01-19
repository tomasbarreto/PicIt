package com.example.picit.winner

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RoomWinnerViewModel: ViewModel() {

    fun leaveRoom(room: PicDescRoom, user:User,callback: () -> Unit = {}){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        // remove user from leaderboard
        val updatedLeaderboard = room.leaderboard.filter { it.userId != user.id }

        // if all users left, remove this room
        if(updatedLeaderboard.isEmpty()){
            roomRef.removeValue().addOnSuccessListener {
                removeRoomForUserRooms(room,user,callback)
            }
        }
        else{
            val updatedRoom = room.copy(leaderboard = updatedLeaderboard)
            roomRef.setValue(updatedRoom).addOnSuccessListener{
                removeRoomForUserRooms(room,user,callback)
            }
        }


    }

    private fun removeRoomForUserRooms(room: PicDescRoom, user:User, callback: () -> Unit = {}){
        val db = Firebase.database
        // remove this room from user rooms
        val userRef = db.getReference("users/${user.id}")

        val updatedPicDescRooms = user.picDescRooms.filter { it != room.id }
        val updatedUser = user.copy(picDescRooms = updatedPicDescRooms)
        userRef.setValue(updatedUser).addOnSuccessListener {
            callback()
        }
    }
}