package com.example.picit.joinroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.entities.GameType
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.AppBottomMenu
import com.example.picit.utils.RoomPreview
import com.example.picit.utils.ScreenHeader

@Composable
fun UserRoomsScreen(
    bottomNavigationsList: List<() -> Unit> = listOf({}, {}, {}),
    onClickJoinRoom: () -> Unit = {},
    onClickCreateRoom: () -> Unit = {},
    onClickInvitesButton: ()-> Unit = {},
    onClickSettings: ()-> Unit = {},
    onClickRoom: (String?, GameType) -> Unit = {_,_ ->},
    userCurrentRepicRooms: List<RePicRoom> = emptyList(),
    userCurrentPicDescRooms: List<PicDescRoom> = emptyList(),
    userID: String = ""
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            text = "Your rooms",
            headerFontSize = 30.sp,
            withSettings = true,
            onClickSettings= {onClickSettings()}
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            SearchBar()
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(0.8f)
        ){
            Button(onClick = { onClickInvitesButton() }) {
                Icon(Icons.Filled.Email, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(5f)
        ) {

            // Rooms of the user, get from databse
            userCurrentRepicRooms.forEach { room ->
                var roomName = room.name
                var roomMaxSize = room.maxCapacity
                var usersInRoom = room.currentCapacity
                var gameType = "RePic" //
                var maxDailyChallenges = room.maxNumOfChallenges
                var challengesDone = room.currentNumOfChallengesDone
                Spacer(modifier = Modifier.height(16.dp))
                RoomPreview(roomName, roomMaxSize, usersInRoom,gameType, maxDailyChallenges,challengesDone,{onClickRoom(room.id, room.gameType)})
            }

            userCurrentPicDescRooms.forEach { room ->
                var roomName = room.name
                var roomMaxSize = room.maxCapacity
                var usersInRoom = room.currentCapacity
                var gameType = "PicDesc" //
                var maxDailyChallenges = room.maxNumOfChallenges
                var challengesDone = room.currentNumOfChallengesDone
                Spacer(modifier = Modifier.height(16.dp))
                RoomPreview(
                    roomName,
                    roomMaxSize,
                    usersInRoom,
                    gameType,
                    maxDailyChallenges,
                    challengesDone,
                    { onClickRoom(room.id, room.gameType) })
            }
        }



        Spacer(modifier = Modifier.weight(1f))

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = { onClickJoinRoom() } ) {
                Text(text = "Join Room")
            }
            Button(onClick = { onClickCreateRoom() }) {
                Text(text = "Create Room")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        AppBottomMenu(selectedItem = 1, onClickForItems =bottomNavigationsList)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") } // TODO: check if its this

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}


@Preview(showBackground = true)
@Composable
fun UserRoomsScreenPreview() {
    PicItTheme {
        UserRoomsScreen()
    }
}