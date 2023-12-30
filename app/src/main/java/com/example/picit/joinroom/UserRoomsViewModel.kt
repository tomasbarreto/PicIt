package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class UserRoomsViewModel: ViewModel() {

    fun getRoomsLists(repicRoomsIds: List<String>, repicRooms: MutableState<List<RePicRoom>>,
                          picDescRoomsIds: List<String>, picDescRooms: MutableState<List<PicDescRoom>>) {
        repicRoomsIds.forEach { roomId ->
            findRepicRoomById(roomId,
                { room: RePicRoom -> repicRooms.value = repicRooms.value + room }
            )
        }
        picDescRoomsIds.forEach { roomId ->
            findPicDescRoomById(roomId,
                { room: PicDescRoom -> picDescRooms.value = picDescRooms.value + room }
            )
        }
    }

    private fun findRepicRoomById(roomId: String, onRoomFound: (RePicRoom) -> Unit) {
        var db = Firebase.database
        val roomsRef = db.getReference("repicRooms")
        roomsRef.child(roomId).get().addOnSuccessListener {
            val savedRoom = it.getValue<RePicRoom>()
            Log.d("firebase", "Got value ${savedRoom}")

            if (savedRoom != null) {
                onRoomFound(savedRoom)
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun findPicDescRoomById(roomId: String, onRoomFound: (PicDescRoom) -> Unit) {
        var db = Firebase.database
        val roomsRef = db.getReference("picDescRooms")
        roomsRef.child(roomId).get().addOnSuccessListener {
            val savedRoom = it.getValue<PicDescRoom>()
            Log.d("firebase", "Got value ${savedRoom}")

            if (savedRoom != null) {
                onRoomFound(savedRoom)
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}