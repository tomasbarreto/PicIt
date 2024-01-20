package com.example.picit.picdesc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.picit.entities.Time
import com.example.picit.timer.Timer
import com.example.picit.timer.TimerViewModel
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun WaitingPhotoDescriptionScreen(
    onClickBackButton: ()->Unit = {},
    onClickLeaderboardButton: () -> Unit = {},
    onClickAddToRoomButton: () -> Unit = {},
    roomName: String = "Room name",
    endingTime: Time = Time(),
    viewModel: TimerViewModel
){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = roomName,
            onClickBackButton = onClickBackButton,
            withAddUsers = true,
            onClickAddUsers = onClickAddToRoomButton
        )

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Timer(timeFor = "Come back later for the \ndescription release!\n", viewModel = viewModel, endingTime = endingTime)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaitingPhotoDescriptionScreen() {
    PicItTheme {
        //WaitingPhotoDescriptionScreen()
    }
}