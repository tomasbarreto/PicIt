package com.example.picit.notifications

import androidx.lifecycle.ViewModel
import com.example.picit.entities.GameType
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.utils.DBUtils

class RoomInviteNotificationsViewModel: ViewModel() {
    val requestsWithRepicRooms = mutableListOf<Pair<JoinRoomRequest, RePicRoom>>()
    val requestsWithPicDescRooms = mutableListOf<Pair<JoinRoomRequest, PicDescRoom>>()
    private val dbutils = DBUtils()

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


}