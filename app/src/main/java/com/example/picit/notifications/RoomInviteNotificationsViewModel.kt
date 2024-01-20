package com.example.picit.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.example.picit.entities.UserInLeaderboard
import com.example.picit.utils.DBUtils
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RoomInviteNotificationsViewModel: ViewModel() {
    var dbUtils = DBUtils()

    fun accept(roomId: String, user: User) {
        dbUtils.findRepicRoomById(roomId) { room ->
            updateUserRepicRooms(user, room
            ) {
                userJoinRoom(user, room)
            }
        }
    }



    fun removeJoinRoomRequest(user: User, request: JoinRoomRequest) {
        var db = Firebase.database
        val userRef = db.getReference("users/${user.id}")
        val updatedRoomRequests = user.requestsToJoin.toMutableList()
        updatedRoomRequests.remove(request)
        val updatedUser = user.copy(requestsToJoin = updatedRoomRequests)
        userRef.setValue(updatedUser)
    }

    fun removeJoinRoomRequests(user: User, roomId: String) {
        user.requestsToJoin.forEach { req ->
            if(req.roomId.equals(roomId)) {
                removeJoinRoomRequest(user, req)
            }
        }
    }


    fun updateUserRepicRooms(user : User, room: RePicRoom, updateJoinRoom: () -> Unit = {}) {
        val database = Firebase.database

        val updatedRepicRooms = user.repicRooms.toMutableList()
        updatedRepicRooms.add(room.id!!)
        Log.w("nfasj,fdns", room.id!!)


        val updatedUser = user.copy(repicRooms = updatedRepicRooms)

        val userRef = database.getReference("users/${user.id}")
        userRef.setValue(updatedUser).addOnSuccessListener {
            Log.w("nfasj,fdns", updatedRepicRooms.toString())
            updateJoinRoom()
        }
    }

    fun userJoinRoom(user : User, room: RePicRoom) {
        val database = Firebase.database

        val updatedCapacity = room.currentCapacity +1
        val updatedLeaderboard = room.leaderboard.toMutableList()
        updatedLeaderboard.add(UserInLeaderboard(user.id,user.username, 0))

        val updatedRoom = room.copy(currentCapacity = updatedCapacity,
            leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("repicRooms/${room.id}")
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            user.requestsToJoin.forEach { req ->
                if(req.roomId.equals(room.id)) {
                    removeJoinRoomRequest(user, req)
                }
            }
        }
    }

}