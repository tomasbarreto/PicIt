package com.example.picit.notifications

import androidx.lifecycle.ViewModel
import com.example.picit.entities.GameType
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.example.picit.utils.DBUtils
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RoomInviteNotificationsViewModel: ViewModel() {
    var requestsWithRepicRooms = mutableListOf<Pair<JoinRoomRequest, RePicRoom>>()
    var requestsWithPicDescRooms = mutableListOf<Pair<JoinRoomRequest, PicDescRoom>>()
    private var dbutils = DBUtils()

    fun getRooms(requests: List<JoinRoomRequest>) {
        requests.forEach { req ->
            if(req.gameType.equals(GameType.REPIC)) {
                dbutils.findRepicRoomById(req.roomId) { room ->
                    requestsWithRepicRooms.add(Pair(req, room)) }
            } else {
                dbutils.findPicDescRoomById(req.roomId) { room ->
                    requestsWithPicDescRooms.add(Pair(req, room)) }
            }
        }
    }

    fun removeRoomRequests(
        requestRepic: Pair<JoinRoomRequest, RePicRoom> = Pair(JoinRoomRequest(), RePicRoom()),
        requestPicDesc: Pair<JoinRoomRequest, PicDescRoom> = Pair(JoinRoomRequest(), PicDescRoom()),
        user: User, gameType: GameType
    ) {
        var db = Firebase.database
        val userRef = db.getReference("users/${user.id}")

        val updatedRequests = user.requestsToJoin.toMutableList()

        if (gameType.equals(GameType.REPIC)) {
            updatedRequests.remove(requestRepic.first)
            requestsWithRepicRooms.remove(requestRepic)
        } else {
            updatedRequests.remove(requestPicDesc.first)
            requestsWithPicDescRooms.remove(requestPicDesc)
        }

        val updatedUser = user.copy(requestsToJoin = updatedRequests)
        userRef.setValue(updatedUser)
    }


}