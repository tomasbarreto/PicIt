package com.example.picit.camera

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.example.picit.location.LocationClient
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import java.util.Calendar


private val TAG = "RePicCameraViewModel"

class RePicCameraViewModel: ViewModel() {


    fun submitImage(room: RePicRoom, user: User, uri: Uri, context: Context,
                    navigationFunction:()->Unit={}){
        val storage = Firebase.storage

        val roomImageStorageRef = storage.getReference("rePic/${room.id}/" +
                "${user.username}P${room.currentNumOfChallengesDone}")

        val uploadImage = roomImageStorageRef.putFile(uri)

        uploadImage.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            roomImageStorageRef.downloadUrl.addOnSuccessListener { uri ->
                // Get the download URL
                val imageUrl = uri.toString()

                // Now that you have the image URL, update the Realtime Database
                updateDatabase(room, user, imageUrl, context){
                    navigationFunction()
                }
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
            Log.e(TAG,exception.toString())
        }

    }


    private fun updateDatabase(room: RePicRoom, user: User, imageUrl: String, context: Context,
                               navigationFunction: () -> Unit) {
        val locationClient = LocationClient()

        val currentCalendar = Calendar.getInstance()
        val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(
            Calendar.MINUTE))


        if (locationClient.isLocationPermGranted(context)) {
            locationClient.startLocationClient(context)
            locationClient.getLocation(context){location->
                Log.d("LOCATION PERMISSION VALUE ", "Entrou na funcao de localizacao")
                insertPhoto(imageUrl, user.id, user.username, location, currentTime, room){
                    navigationFunction()
                }

            }
        }
        else{
            insertPhoto(imageUrl, user.id, user.username, "", currentTime, room){
                navigationFunction()
            }
        }


    }
    private fun insertPhoto(imageUrl:String, userId:String, username:String, location:String,
                            time:Time, room: RePicRoom, navigationFunction: () -> Unit){
        val db = Firebase.database
        val roomRef = db.getReference("repicRooms/${room.id}")

        // Create RePicPhoto with the image URL
        val photo = RePicPhoto(
            username = username,
            photoUrl = imageUrl,
            userId = userId,
            submissionTime = time,
            location = location,
        )
        val index = room.currentNumOfChallengesDone
        var updatedSubmittedPhotosInChallenge = if(room.photosSubmitted.size==index) mutableListOf()
                                    else room.photosSubmitted[index].filter{ it.userId != userId }.toMutableList()
        updatedSubmittedPhotosInChallenge.add(photo)

        val photosSubmittedUpdated = room.photosSubmitted.toMutableList()
        if(photosSubmittedUpdated.size == index){
            photosSubmittedUpdated.add(updatedSubmittedPhotosInChallenge)
        }
        else{
            photosSubmittedUpdated[index] = updatedSubmittedPhotosInChallenge
        }

        // Update the room object in the Realtime Database
        val updatedRoom = room.copy(photosSubmitted = photosSubmittedUpdated)
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            navigationFunction()
         }
    }


}
