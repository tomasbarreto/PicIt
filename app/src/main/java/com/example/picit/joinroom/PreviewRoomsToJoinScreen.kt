package com.example.picit.joinroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.R
import com.example.picit.utils.RoomPreview
import com.example.picit.utils.ScreenHeader
import com.example.picit.ui.theme.PicItTheme

@Composable
fun PreviewRoomsToJoinScreen(
    onClickBackButton: ()->Unit={},
    roomsAvailable: List<PreviewRoom>
){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            text = "Join Room",
            withBackButton = true,
            onClickBackButton = onClickBackButton
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                onClick = { /*TODO: screen/window to filter rooms*/ },
                colors = ButtonDefaults.buttonColors(Color.Transparent)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "filter",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }

        // Public rooms in the system, get from databse
        var nRooms = 2;
        for (i in 1..nRooms){
            var roomName = "Room Name"
            var roomMaxSize = 10
            var usersInRoom = 9
            var gameType = "RePic" //
            var maxDailyChallenges = 30
            var challengesDone = 13
            Spacer(modifier = Modifier.height(16.dp))
            RoomPreview(roomName, roomMaxSize, usersInRoom,gameType, maxDailyChallenges,challengesDone)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Join a Private Room", fontSize = 24.sp,modifier = Modifier.padding(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRoomsToJoinScreenPreview() {
    PicItTheme {
        PreviewRoomsToJoinScreen()
    }
}