package com.example.picit.repic


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.picit.R
import com.example.picit.entities.RePicRoom
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.timer.Timer
import com.example.picit.timer.TimerViewModel
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader
import com.example.picit.utils.TakePhotoButton

@Composable
fun RepicRoomTakePicture(
    onClickBackButton: ()->Unit = {},
    onClickCameraButton: ()->Unit = {},
    onClickLeaderboardButton: () -> Unit = {},
    viewModel: TimerViewModel,
    room: RePicRoom = RePicRoom()
){

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = room.name,
            onClickBackButton = onClickBackButton
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text="Photo To Repic:", fontSize = 30.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = room.imagesUrls[room.currentNumOfChallengesDone],
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight(0.45f)
                    .fillMaxWidth(0.8f)
            )
        }

        Timer(timeFor = "Submit Photo", viewModel = viewModel, room.winnerAnnouncementTime)
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            TakePhotoButton(onButtonClick = onClickCameraButton)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.End) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LeaderboardButton(onClickLeaderboardButton)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepicRoomTakePicturePreview() {
    PicItTheme {
        RepicRoomTakePicture(viewModel=viewModel())
    }
}