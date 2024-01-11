package com.example.picit.picdesc

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database

class SubmitPhotoDescriptionViewModel: ViewModel() {

    fun submitPhotoDescription(photoDescription: String, picDescRoom: PicDescRoom) {
        val database = Firebase.database
        val picDescRoomRef = database.getReference("picDescRooms/${picDescRoom.id}")

        val updatedRoom = picDescRoom.copy(photoDescription = photoDescription)

        picDescRoomRef.setValue(updatedRoom)
    }

}