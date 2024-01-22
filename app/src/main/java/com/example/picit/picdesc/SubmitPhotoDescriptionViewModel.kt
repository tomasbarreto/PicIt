package com.example.picit.picdesc

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

private var database: FirebaseDatabase = Firebase.database

class SubmitPhotoDescriptionViewModel: ViewModel() {

    fun submitPhotoDescription(photoDescription: String, room: PicDescRoom, context: Context) {
        val picDescRoomRef = database.getReference("picDescRooms/${room.id}")

        val index = room.currentNumOfChallengesDone
        val updatedPhotoDescriptions = room.photoDescriptions.toMutableList()
        if(updatedPhotoDescriptions.size == index){
            updatedPhotoDescriptions.add(photoDescription)
        }
        else{
            updatedPhotoDescriptions[index] = photoDescription
        }

        val updatedRoom = room.copy(photoDescriptions = updatedPhotoDescriptions)

        picDescRoomRef.setValue(updatedRoom).addOnSuccessListener {
            Toast.makeText(
                context,
                "Authentication failed",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    fun resetInfo(room: PicDescRoom, userId: String, callback: ()->Unit={}) {
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
        roomsRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }
    }



}