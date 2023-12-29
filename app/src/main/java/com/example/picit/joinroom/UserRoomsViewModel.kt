package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class UserRoomsViewModel: ViewModel() {

    fun getRoomsList(currentUserRoomsIds: List<String>, currentRooms: MutableState<List<RePicRoom>>) {
        currentUserRoomsIds.forEach { roomId ->
            findRoomById(roomId,
                { room: RePicRoom -> currentRooms.value = currentRooms.value + room }
            )
        }
    }


    private fun findRoomById(roomId: String, onRoomFound: (RePicRoom) -> Unit) {
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

}