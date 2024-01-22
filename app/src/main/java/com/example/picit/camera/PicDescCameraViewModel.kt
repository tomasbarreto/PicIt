package com.example.picit.camera

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.example.picit.location.LocationClient
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import java.util.Calendar

private val TAG = "PicDescCameraViewModel"
class PicDescCameraViewModel: ViewModel() {

    fun submitImage(room: PicDescRoom, user: User, uri: Uri, context: Context,
                    navigationFunction:()->Unit={}){
        val storage = Firebase.storage


        val roomImageStorageRef = storage.getReference("picDesc/${room.id}/" +
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
        }


    }


    private fun updateDatabase(room: PicDescRoom, user: User, imageUrl: String, context: Context,
                               navigationFunction: () -> Unit) {
        val locationClient = LocationClient()

        val currentCalendar = Calendar.getInstance()
        val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(
            Calendar.MINUTE))


        if (locationClient.isLocationPermGranted(context)) {
            locationClient.startLocationClient(context)
            locationClient.getLocation(context){location->
                Log.d(TAG,"Location $location")

                insertPhoto(imageUrl,user.id, user.username, location, currentTime, room){
                    navigationFunction()
                }
            }
        }
        else{
            insertPhoto(imageUrl,user.id, user.username, "", currentTime, room){
                navigationFunction()
            }
        }


    }

    private fun insertPhoto(imageUrl:String, userId:String,username:String ,location:String,
                            time:Time, room:PicDescRoom,navigationFunction: () -> Unit){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")


        // Create PicDescPhoto with the image URL
        val photo = PicDescPhoto(
            photoUrl = imageUrl,
            userId = userId,
            username = username,
            location = location,
            submissionTime = time,
            usersThatVoted = emptyList(),
            leaderVote = false,
            ratingSum = 0
        )
        val index = room.currentNumOfChallengesDone
        val updatedSubmittedPhotosInChallenge =
            if(room.allPhotosSubmitted.size==index) mutableListOf()
            else room.allPhotosSubmitted[index].filter { it.userId != userId }.toMutableList()
        updatedSubmittedPhotosInChallenge.add(photo)

        val updatedSubmittedPhotos = room.allPhotosSubmitted.toMutableList()
        if(updatedSubmittedPhotos.size == index){
            updatedSubmittedPhotos.add(updatedSubmittedPhotosInChallenge)
        }
        else{
            updatedSubmittedPhotos[index] = updatedSubmittedPhotosInChallenge
        }


        // Update the room object in the Realtime Database
        val updatedRoom = room.copy(allPhotosSubmitted = updatedSubmittedPhotos)
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            navigationFunction()
        }
    }


}