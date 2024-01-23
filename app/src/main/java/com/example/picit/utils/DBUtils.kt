package com.example.picit.utils

import android.content.ContentValues.TAG
import android.util.Log
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class DBUtils() {

    private var db = Firebase.database
    private lateinit var picDescRoomEventListener: ValueEventListener
    private lateinit var userEventListener: ValueEventListener
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

    fun setCurrentUserListener(uid: String, currentUserUpdate: (User) -> Unit) {
        val userRef = db.getReference("users/$uid")

        val eventListener = object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()

                if(user != null){
                    user.id = uid;
                    currentUserUpdate(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }
        userRef.addValueEventListener(eventListener)
        userEventListener = eventListener
    }

    fun removePicDescListener(roomId:String){
        val roomRef = db.getReference("picDescRooms/$roomId")

        roomRef.removeEventListener(picDescRoomEventListener)
    }

    fun removeCurrentUserListener(userId:String) {
        val userRef = db.getReference("users/$userId")

        userRef.removeEventListener(userEventListener)
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

    fun incrementUserNumWins(user: User){
        val db = Firebase.database
        val userRef = db.getReference("users/${user.id}")

        var updatedTotalWins = user.totalWins
        updatedTotalWins += 1
        val updatedUser = user.copy(totalWins = updatedTotalWins)

        userRef.setValue(updatedUser)
    }
}