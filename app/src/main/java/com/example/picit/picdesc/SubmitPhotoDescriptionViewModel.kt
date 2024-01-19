package com.example.picit.picdesc

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

private var database: FirebaseDatabase = Firebase.database

class SubmitPhotoDescriptionViewModel: ViewModel() {

    fun submitPhotoDescription(photoDescription: String, picDescRoom: PicDescRoom) {
        val picDescRoomRef = database.getReference("picDescRooms/${picDescRoom.id}")

        val updatedRoom = picDescRoom.copy(photoDescription = photoDescription)

        picDescRoomRef.setValue(updatedRoom)
    }

    fun resetInfo(room: PicDescRoom, userId: String) {
        val roomsRef = database.getReference("picDescRooms/${room.id}")

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

        val updatedRoom = room.copy(leaderboard = updatedLeaderboard)
        roomsRef.setValue(updatedRoom)
    }



}