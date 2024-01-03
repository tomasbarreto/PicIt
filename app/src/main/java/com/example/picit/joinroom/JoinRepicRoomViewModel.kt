package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
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

    // TODO: esta funcao eh repetida !! nos createRoomViewModels
    fun updateUserRepicRooms(currentUserRooms: List<String>, currentUserId: String) {
        val database = Firebase.database

        val userCurrentRooms = currentUserRooms + repicRoom.id

        val roomsRef = database.getReference("users/$currentUserId/repicRooms")
        roomsRef.setValue(userCurrentRooms)
    }

    fun incrementCurrentCapacityOfRoom() {
        val database = Firebase.database
        val roomCapacityRef = database.getReference("repicRooms/${repicRoom.id}/currentCapacity")
        roomCapacityRef.setValue(repicRoom.currentCapacity+1)
    }
}


