package com.example.picit.repic

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.RePicRoom
import com.example.picit.network.RequestModel
import com.example.picit.network.StableDiffusionApi
import com.example.picit.network.TextPrompt
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch

private val TAG = "RepicRoomPictureReleasedViewModel"

class RepicRoomTakePictureViewModel: ViewModel() {


    fun getGeneratedImage(room: RePicRoom){
        val storage = Firebase.storage
        val roomImagesGenerated = storage.getReference("rePic/${room.id}/" +
                "Challenge${room.currentNumOfChallengesDone}")

        viewModelScope.launch {
            val requestBody = RequestModel(
                listOf(
                    TextPrompt(text = "A painting of a cat", weight = 1.0),
                    TextPrompt(text= "blurry, bad", weight = -1.0)
                )
            )
            val generatedImage = StableDiffusionApi.retrofitService.getGeneratedImage(requestBody)

            val imageByteArray = Base64.decode(generatedImage.artifacts[0].base64, Base64.DEFAULT)

            val uploadImage = roomImagesGenerated.putBytes(imageByteArray)

            uploadImage.addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                roomImagesGenerated.downloadUrl.addOnSuccessListener { uri ->
                    // Get the download URL
                    val imageUrl = uri.toString()

                    // Now that you have the image URL, update the Realtime Database
                    updateDatabase(room, imageUrl)
                }
            }.addOnFailureListener { exception ->
                // Handle unsuccessful uploads
            }

        }
    }

    private fun updateDatabase(room: RePicRoom, imageUrl: String){
        val db = Firebase.database
        val roomRef = db.getReference("repicRooms/${room.id}")

       val updatedRoom = room.copy(imageUrl = imageUrl)
        roomRef.setValue(updatedRoom)
    }
}