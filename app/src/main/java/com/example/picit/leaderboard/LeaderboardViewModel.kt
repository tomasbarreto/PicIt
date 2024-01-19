package com.example.picit.leaderboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class LeaderboardViewModel: ViewModel() {

    var usersInLeaderboard by mutableStateOf(emptyList<UserInLeaderboard>())

    fun getLeaderboardPicDesc(room: PicDescRoom) {
        val db = Firebase.database
        val picdescRoomRef = db.getReference("picDescRooms/" + room.id)

        picdescRoomRef.get().addOnSuccessListener { picdescRoomSnapshot ->
            var picDescRoom = picdescRoomSnapshot.getValue<PicDescRoom>()
            if (picDescRoom != null) {
                usersInLeaderboard = picDescRoom.leaderboard
                usersInLeaderboard = usersInLeaderboard.sortedByDescending { it.points }
            }

            Log.d("firebase", "PICDESC LEADERBOARD: $usersInLeaderboard")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun getLeaderboardRepic(room: RePicRoom) {
        val db = Firebase.database
        val repicRoomRef = db.getReference("repicRooms/" + room.id)

        repicRoomRef.get().addOnSuccessListener { repicRoomSnapshot ->
            var repicRoom = repicRoomSnapshot.getValue<RePicRoom>()
            if (repicRoom != null) {
                usersInLeaderboard = repicRoom.leaderboard
                usersInLeaderboard = usersInLeaderboard.sortedByDescending { it.points }

            }

            Log.d("firebase", "REPIC LEADERBOARD: $usersInLeaderboard")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}