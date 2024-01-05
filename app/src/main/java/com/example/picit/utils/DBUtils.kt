package com.example.picit.utils

import android.util.Log
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class DBUtils() {

    private var db = Firebase.database

    fun findRepicRoomById(roomId: String, onRoomFound: (RePicRoom) -> Unit) {
        val roomsRef = db.getReference("repicRooms")
        roomsRef.child(roomId).get().addOnSuccessListener {
            val savedRoom = it.getValue<RePicRoom>()
            if(savedRoom != null){
                savedRoom.id = roomId
                Log.d("firebase", "Got value ${savedRoom}")
                onRoomFound(savedRoom)
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun findPicDescRoomById(roomId: String, onRoomFound: (PicDescRoom) -> Unit) {
        val roomsRef = db.getReference("picDescRooms")
        roomsRef.child(roomId).get().addOnSuccessListener {
            val savedRoom = it.getValue<PicDescRoom>()
            if (savedRoom != null) {
                savedRoom.id = roomId
                Log.d("firebase", "Got value ${savedRoom}")
                onRoomFound(savedRoom)
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}