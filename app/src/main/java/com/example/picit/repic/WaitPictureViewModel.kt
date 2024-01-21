package com.example.picit.repic

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

private val TAG = "WaitPictureViewModel"

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

        var updatedRoom: RePicRoom
        pictureWasAlreadyUpdated(room) {
            Log.d(TAG, "here")
            updatedRoom = if (it) {
                room.copy(leaderboard = updatedLeaderboard, photosSubmitted = emptyList())
            } else {
                room.copy(leaderboard = updatedLeaderboard, photosSubmitted = emptyList(), imageUrl = "")
            }
            roomsRef.setValue(updatedRoom).addOnSuccessListener {
                callback()

            }
        }
    }

    private fun pictureWasAlreadyUpdated(room: RePicRoom, getResult: (Boolean) -> Unit) {
        val storage = Firebase.storage
        val picRef= storage.getReference("rePic/${room.id}/Challenge${room.currentNumOfChallengesDone}")

        picRef.downloadUrl.addOnSuccessListener {
            Log.d(TAG, "Had photo")
            getResult(true)
        }.addOnFailureListener {
            Log.d(TAG, "Didnt have photo")
            getResult(false)
        }
    }
}