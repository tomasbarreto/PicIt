package com.example.picit.utils

import android.util.Log
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class DBUtils() {

    private var db = Firebase.database
    private lateinit var picDescRoomEventListener: ValueEventListener
    private lateinit var rePicRoomEventListener: ValueEventListener

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

    fun setPicDescRoomListener(roomId: String, onUpdate: (PicDescRoom) -> Unit) {
        val roomRef = db.getReference("picDescRooms/$roomId")

        val eventListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val room = snapshot.getValue<PicDescRoom>()

                if(room != null){
                    onUpdate(room)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data $error")
            }
        }
        roomRef.addValueEventListener(eventListener)
        picDescRoomEventListener = eventListener
    }

    fun removePicDescListener(roomId:String){
        val roomRef = db.getReference("picDescRooms/$roomId")

        roomRef.removeEventListener(picDescRoomEventListener)
    }

    fun removeRePicListener(roomId:String){
        val roomRef = db.getReference("repicRooms/$roomId")

        roomRef.removeEventListener(rePicRoomEventListener)
    }

    fun setRePicRoomListener(roomId: String,onUpdate: (RePicRoom) -> Unit) {
        val roomRef = db.getReference("repicRooms/$roomId")

        val eventListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val room = snapshot.getValue<RePicRoom>()

                if(room != null){
                    onUpdate(room)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data $error")
            }
        }
        roomRef.addValueEventListener(eventListener)
        rePicRoomEventListener = eventListener
    }
}