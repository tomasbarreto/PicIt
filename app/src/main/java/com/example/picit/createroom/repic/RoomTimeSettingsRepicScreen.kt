package com.example.picit.picdesccreateroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import com.example.picit.entities.Time
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun RoomTimeSettingsRepicScreen(
    modifier: Modifier = Modifier,
    onClickBackButton: ()->Unit = {},
    roomName: String = "",
    roomCapacity: String = "",
    numChallenges: String = "",
    privacy: Boolean = false,
    privacyCode: String = "",
    onClickGoHomeScreen: ()->Unit = {},
    currentUserId: String = "",
    currentUserName: String = "",
    currentUserRooms: List<String> = emptyList()

) {
    val viewModel : RepicRoomTimeSettingsViewModel = viewModel()

    var hoursPictureRelease = remember { mutableStateOf("") }
    var minutesPictureRelease = remember { mutableStateOf("") }

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


        Text(text = "Winner announcement time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        InsertTime(hours = hoursWinner, minutes = minutesWinner)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            viewModel.registerRepicRoom(roomName, roomCapacity, numChallenges, privacy, privacyCode,
                Time(hoursPictureRelease.value.toInt(), minutesPictureRelease.value.toInt()),
                Time(hoursWinner.value.toInt(), minutesWinner.value.toInt()),
                onClickGoHomeScreen, currentUserRooms, currentUserId, currentUserName)

        }) {
            Text(text = "Next", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun RoomTimeSettingsRepicPreview() {
    PicItTheme {
        RoomTimeSettingsRepicScreen()
    }
}
