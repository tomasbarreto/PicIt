package com.example.picit.camera

import android.content.Context
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicPhoto
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


private val TAG = "RePicCameraViewModel"

class RePicCameraViewModel: ViewModel() {
    val dbUtils = DBUtils()

    fun submitImage(room: RePicRoom, user: User, uri: Uri, context: Context,
                    navigationFunction:()->Unit={}){
        val storage = Firebase.storage

        val roomImageStorageRef = storage.getReference("rePic/${room.id}/" +
                "${user.username}P${room.currentNumOfChallengesDone}")

        val uploadImage = roomImageStorageRef.putFile(uri)

        uploadImage.addOnSuccessListener { taskSnapshot ->
            Log.d(TAG, taskSnapshot.uploadSessionUri.toString())
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


        if (locationClient.isLocationPermGranted(context) && isGpsEnabled(context)) {
            locationClient.startLocationClient(context)
            locationClient.getLocation(context){location->
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

        dbUtils.incrementUserNumPhotosSubmited(user)


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
        roomRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val currentRoom = currentData.getValue<RePicRoom>()!!

                val updatedSubmittedPhotosInChallenge =
                    if (currentRoom.photosSubmitted.size == index) mutableListOf()
                    else currentRoom.photosSubmitted[index].filter { it.userId != userId }.toMutableList()
                updatedSubmittedPhotosInChallenge.add(photo)

                val photosSubmittedUpdated = currentRoom.photosSubmitted.toMutableList()
                if (photosSubmittedUpdated.size == index) {
                    photosSubmittedUpdated.add(updatedSubmittedPhotosInChallenge)
                } else {
                    photosSubmittedUpdated[index] = updatedSubmittedPhotosInChallenge
                }

                currentData.value = currentRoom.copy(photosSubmitted = photosSubmittedUpdated)
                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                dataSnapshot: DataSnapshot?
            ) {
                if (committed) {
                    Log.d(TAG,dataSnapshot.toString())
                } else {
                    // Handle the case where the transaction failed
                }
            }
        })
    }

    private fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if GPS is enabled
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}
