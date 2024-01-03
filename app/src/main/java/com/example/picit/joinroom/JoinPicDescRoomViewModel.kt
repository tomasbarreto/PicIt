package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class JoinPicDescRoomViewModel : ViewModel() {
    var picDescRoom by mutableStateOf(PicDescRoom())

    fun loadPicDescRoom(roomId: String) {
        findPicDescRoom(roomId, { room -> picDescRoom = room})
    }

    private fun findPicDescRoom(roomId: String, getRoom: (PicDescRoom) -> Unit) {
        val db = Firebase.database
        val picDescRoomRef = db.getReference("picDescRooms/$roomId")
        picDescRoomRef.get().addOnSuccessListener { picdescRoomSnapshot ->
                val picDescRoom = picdescRoomSnapshot.getValue<PicDescRoom>() // doesnt have the id

                if(picDescRoom != null){
                    val roomId = picdescRoomSnapshot.key.toString()
                    picDescRoom.id = roomId
                    getRoom(picDescRoom)
                }

            Log.d("firebase", "PicDesc ROOM: $picDescRoom")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun updateUserPicDescRooms(user: User) {
        val database = Firebase.database

        val updatedPicDescRooms = user.picDescRooms.toMutableList()
        updatedPicDescRooms.add(picDescRoom.id!!)

        val updatedUser = user.copy(picDescRooms = updatedPicDescRooms)

        val userRef = database.getReference("users/${user.id}")
        userRef.setValue(updatedUser)
    }

    fun incrementCurrentCapacityOfRoom() {
        val database = Firebase.database

        val updatedCapacity = picDescRoom.currentCapacity +1
        val updatedRoom = picDescRoom.copy(currentCapacity = updatedCapacity)

        val roomRef = database.getReference("picDescRooms/${picDescRoom.id}")
        roomRef.setValue(updatedRoom)
    }
}


