package com.example.picit.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoomPreview(roomName: String, roomMaxSize: Int, usersInRoom: Int, gameType: String, maxDailyChallenges: Int, challengesDone: Int) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth(.8f)
            .height(160.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { } // TODO: vai ser preciso um room id para direcionar para a salar certa?
            .padding(12.dp)
    ) {
        Text(text = roomName, fontSize = 20.sp)
        Row{
            Text(text = "$usersInRoom/$roomMaxSize" )
            Icon(Icons.Filled.Person, contentDescription = null)
        }
        Row{
            Text(text = gameType)
        }
        Row{
            Text(text = "$challengesDone/$maxDailyChallenges")
            Icon(Icons.Filled.Done, contentDescription = null)
        }
    }
}