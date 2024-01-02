package com.example.picit.joinroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom

class JoinRepicRoomViewModel : ViewModel() {
    var repicRoom by mutableStateOf(RePicRoom())

    fun getRepicRoom(roomId: String?) {
        
    }
}