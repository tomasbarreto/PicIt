package com.example.picit.joinroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class PreviewRoomsToJoinViewModel: ViewModel() {
    var repicRooms by mutableStateOf(emptyList<RePicRoom>())

    var picdescRooms by
        mutableStateOf(emptyList<PicDescRoom>())


    init{
        getAllRooms(
            retrieveRepicRooms = { allRepicRooms ->
                val openRooms = allRepicRooms.filterNot{ it.privacy }

                repicRooms =openRooms
            },
            retrievePicdescRooms = {allPicdescRooms ->
                val openRooms = allPicdescRooms.filterNot{ it.privacy }
                picdescRooms =openRooms

            }
        )
    }

    // rooms are available to join if they're public and the user isnt currently in them
    fun filterRoomsUserIsIn(repicRoomsIdsUserIsIn : List<String>, picdescRoomsIdsUserIsIn : List<String>){
        repicRooms =  repicRooms.filterNot { repicRoomsIdsUserIsIn.contains(it.id) }
        picdescRooms =  picdescRooms.filterNot { picdescRoomsIdsUserIsIn.contains(it.id) }
    }

}

// TODO: funcoes do firebase numa classe aparte? incluindo o findUserById
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

