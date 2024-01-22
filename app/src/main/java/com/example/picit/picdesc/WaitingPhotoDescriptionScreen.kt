package com.example.picit.picdesc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.picit.entities.Time
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.timer.Timer
import com.example.picit.timer.TimerViewModel
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun WaitingPhotoDescriptionScreen(
    onClickBackButton: ()->Unit = {},
    onClickLeaderboardButton: () -> Unit = {},
    roomName: String = "Room name",
    endingTime: Time = Time(),
    viewModel: TimerViewModel,
    isLeader: Boolean = false
){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = roomName,
            onClickBackButton = onClickBackButton
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            val step = if (isLeader) "submission" else "release"
            Timer(timeFor = "Come back later for the \ndescription $step!\n", viewModel = viewModel, endingTime = endingTime)
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ){ LeaderboardButton(onClickButton = onClickLeaderboardButton)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaitingPhotoDescriptionScreen() {
    PicItTheme {
        WaitingPhotoDescriptionScreen(viewModel=viewModel())
    }
}