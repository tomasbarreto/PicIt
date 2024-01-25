package com.example.picit.camera

import android.content.Context
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.example.picit.location.LocationClient
import com.example.picit.utils.DBUtils
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.storage
import java.util.Calendar

private val TAG = "PicDescCameraViewModel"
class PicDescCameraViewModel: ViewModel() {
    val dbUtils = DBUtils()

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
                Log.d(TAG, imageUrl)
                // Now that you have the image URL, update the Realtime Database
                updateDatabase(room, user, imageUrl, context) {
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


        if (locationClient.isLocationPermGranted(context) && isGpsEnabled(context)) {
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

        dbUtils.incrementUserNumPhotosSubmited(user)
    }

     fun insertPhoto(imageUrl:String, userId:String,username:String ,location:String,
                            time:Time, room:PicDescRoom,callback: () -> Unit){
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

         roomRef.runTransaction(object : Transaction.Handler{
             override fun doTransaction(currentData: MutableData): Transaction.Result {
                 val currentRoom = currentData.getValue<PicDescRoom>()!!
                 val updatedSubmittedPhotosInChallenge =
                     if(currentRoom.allPhotosSubmitted.size==index) mutableListOf()
                     else currentRoom.allPhotosSubmitted[index].filter { it.userId != userId }.toMutableList()
                 updatedSubmittedPhotosInChallenge.add(photo)

                 val updatedSubmittedPhotos = currentRoom.allPhotosSubmitted.toMutableList()
                 if(updatedSubmittedPhotos.size == index){
                     updatedSubmittedPhotos.add(updatedSubmittedPhotosInChallenge)
                 }
                 else{
                     updatedSubmittedPhotos[index] = updatedSubmittedPhotosInChallenge
                 }


                 // Update the room object in the Realtime Database
                 val updatedRoom = currentRoom.copy(allPhotosSubmitted = updatedSubmittedPhotos)
                 currentData.value = updatedRoom
                 return Transaction.success(currentData)
             }

             override fun onComplete(
                 error: DatabaseError?,
                 committed: Boolean,
                 currentData: DataSnapshot?
             ) {
                 if (committed) {
                     Log.d(TAG,currentData.toString())
                 } else {
                     // Handle the case where the transaction failed
                 }
                 callback()
             }

         })

    }

    private fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if GPS is enabled
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun addDefaultDescription(room: PicDescRoom) {
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        val defaultDescription = "Yellow car"

        var updatedDescriptions = mutableListOf<String>()

        for (description in room.photoDescriptions)
            updatedDescriptions.add(description)

        updatedDescriptions.add(defaultDescription)

        // Update the room object in the Realtime Database
        val updatedRoom = room.copy(photoDescriptions = updatedDescriptions)
        roomRef.setValue(updatedRoom)
    }


}