package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class UserRoomsViewModel: ViewModel() {

    var userRepicRooms by mutableStateOf(emptyList<RePicRoom>())
    var userPicdescRooms by mutableStateOf(emptyList<PicDescRoom>())

    init{
        getAllRooms(
            retrieveRepicRooms = { allRepicRooms -> userRepicRooms = allRepicRooms},
            retrievePicdescRooms = {allPicdescRooms -> userPicdescRooms =allPicdescRooms }
        )
    }

    fun filterRoomsUserIsNotIn(repicRoomsIds: List<String>, picDescRoomsIds: List<String>) {
        userRepicRooms = userRepicRooms.filter { room -> room.id in repicRoomsIds }
        userPicdescRooms = userPicdescRooms.filter { room -> room.id in picDescRoomsIds }
    }

    //TODO repetido em previewRoomsToJoinViewModel
    private fun getAllRooms(
        retrievePicdescRooms: (List<PicDescRoom>) -> Unit,
        retrieveRepicRooms: (List<RePicRoom>) -> Unit,
    ) {
        val db = Firebase.database
        val repicRoomsRef = db.getReference("repicRooms")
        val picdescRoomsRef = db.getReference("picDescRooms")
        repicRoomsRef.get().addOnSuccessListener { repicRoomsListSnapshot ->
            var repicRooms = mutableListOf<RePicRoom>()
            for(roomSnapshtop in repicRoomsListSnapshot.children){
                val repicRoom = roomSnapshtop.getValue<RePicRoom>() // doesnt have the id

                if(repicRoom != null){
                    val roomId = roomSnapshtop.key.toString()
                    repicRoom.id = roomId
                    repicRooms.add(repicRoom)
                }

            }
            Log.d("firebase", "ALL REPIC ROOMS: $repicRooms")
            retrieveRepicRooms(repicRooms)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        picdescRoomsRef.get().addOnSuccessListener { picdescRoomsListSnapshot ->
            var picDescRooms = mutableListOf<PicDescRoom>()
            for(roomSnapshtop in picdescRoomsListSnapshot.children){
                val picdescRoom = roomSnapshtop.getValue<PicDescRoom>() // doesnt have the id

                if(picdescRoom != null){
                    val roomId = roomSnapshtop.key.toString()
                    picdescRoom.id=roomId
                    picDescRooms.add(picdescRoom)
                }

            }
            Log.d("firebase", "ALL PICDESC ROOMS: $picDescRooms")
            retrievePicdescRooms(picDescRooms)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
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