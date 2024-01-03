package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class JoinRepicRoomViewModel : ViewModel() {
    var repicRoom by mutableStateOf(RePicRoom())

    fun loadRepicRoom(roomId: String) {
        findRepicRoom(roomId, { room -> repicRoom = room})
    }

    private fun findRepicRoom(roomId: String, getRoom: (RePicRoom) -> Unit) {
        val db = Firebase.database
        val repicRoomRef = db.getReference("repicRooms/$roomId")
        repicRoomRef.get().addOnSuccessListener { repicRoomSnapshot ->
                val repicRoom = repicRoomSnapshot.getValue<RePicRoom>() // doesnt have the id

                if(repicRoom != null){
                    val roomId = repicRoomSnapshot.key.toString()
                    repicRoom.id = roomId
                    getRoom(repicRoom)
                }

            Log.d("firebase", "REPIC ROOM: $repicRoom")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun updateUserRepicRooms(user : User) {
        val database = Firebase.database

        val updatedRepicRooms = user.repicRooms.toMutableList()
        updatedRepicRooms.add(repicRoom.id!!)

        val updatedUser = user.copy(repicRooms = updatedRepicRooms)

        val userRef = database.getReference("users/${user.id}")
        userRef.setValue(updatedUser)
    }

    fun incrementCurrentCapacityOfRoom() {
        val database = Firebase.database

        val updatedCapacity = repicRoom.currentCapacity +1
        val updatedRoom = repicRoom.copy(currentCapacity = updatedCapacity)

        val roomRef = database.getReference("repicRooms/${repicRoom.id}")
        roomRef.setValue(updatedRoom)
    }
}


