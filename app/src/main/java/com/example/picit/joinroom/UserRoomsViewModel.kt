package com.example.picit.joinroom

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRoomsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(Pair(emptyList<RePicRoom>(), emptyList<PicDescRoom>()))
    val uiState: StateFlow<Pair<List<RePicRoom>, List<PicDescRoom>>> = _uiState.asStateFlow()

    fun getRoomsLists(repicRoomsIds: List<String>, picDescRoomsIds: List<String>) {

        repicRoomsIds.forEach { roomId ->
            findRepicRoomById(roomId,
                { room: RePicRoom ->
                    _uiState.update { currentState ->
                       currentState.copy(first = currentState.first + room)
                    }
                }
            )
        }
        picDescRoomsIds.forEach { roomId ->
            findPicDescRoomById(roomId,
                { room: PicDescRoom ->
                    _uiState.update { currentState ->
                        currentState.copy(second = currentState.second + room)
                    }
                }
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