package com.example.picit.picdesccreateroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.picit.createroom.InsertTime
import com.example.picit.createroom.repic.RepicRoomTimeSettingsViewModel
import com.example.picit.entities.User
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomTimeSettingsRepicScreen(
    modifier: Modifier = Modifier,
    onClickBackButton: ()->Unit = {},
    roomName: String,
    roomCapacity: String,
    numChallenges: String,
    onClickGoHomeScreen: ()->Unit = {},
    currentUserId: String,
    currentUser: User

) {
    val viewModel : RepicRoomTimeSettingsViewModel = viewModel()

    var hoursPictureRelease = remember { mutableStateOf("") }
    var minutesPictureRelease = remember { mutableStateOf("") }

    var hoursPictureSubmissionStart = remember { mutableStateOf("") }
    var minutesPictureSubmissionStart = remember { mutableStateOf("") }

    var hoursPictureSubmissionEnd = remember { mutableStateOf("") }
    var minutesPictureSubmissionEnd = remember { mutableStateOf("") }

    var hoursWinner = remember { mutableStateOf("") }
    var minutesWinner = remember { mutableStateOf("") }

    var maxHours = 23
    var maxMinutes = 59


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            true,
            "Create room",
            onClickBackButton = onClickBackButton
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Picture release time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        InsertTime(hours = hoursPictureRelease, minutes = minutesPictureRelease)

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Photo submission time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InsertTime(hours = hoursPictureSubmissionStart, minutes = minutesPictureSubmissionStart)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = " to ", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            InsertTime(hours = hoursPictureSubmissionEnd, minutes = minutesPictureSubmissionEnd)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Winner announcement time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        InsertTime(hours = hoursWinner, minutes = minutesWinner)

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            viewModel.registerRepicRoom(roomName, roomCapacity, numChallenges,
                hoursPictureRelease.value + ":" + minutesPictureRelease.value,
                hoursPictureSubmissionStart.value + ":" + minutesPictureSubmissionEnd.value,
                hoursPictureSubmissionEnd.value + ":" + minutesPictureSubmissionEnd.value,
                hoursWinner.value + ":" + minutesWinner.value, onClickGoHomeScreen, currentUser, currentUserId)

        }) {
            Text(text = "Next", fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomTimeSettingsRepicPreview() {
    PicItTheme {
//        RoomTimeSettingsRepicScreen()
    }
}
