package com.example.picit.repic

import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database

class WaitPictureViewModel: ViewModel() {

    fun resetInfo(room: RePicRoom, userId: String, callback: ()->Unit={}) {
        val db = Firebase.database
        val roomsRef = db.getReference("repicRooms/${room.id}")

        val updatedLeaderboard = mutableListOf<UserInLeaderboard>()

        for (userInLeaderboard in room.leaderboard){
            if(userInLeaderboard.userId == userId){
                val updatedUserInLeaderboard = userInLeaderboard.copy(didSeeWinnerScreen = false)
                updatedLeaderboard.add(updatedUserInLeaderboard)
            }
            else{
                updatedLeaderboard.add(userInLeaderboard)
            }
        }

        val updatedRoom = room.copy(leaderboard = updatedLeaderboard, photosSubmitted = emptyList(), imageUrl = "")
        roomsRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }
    }
}