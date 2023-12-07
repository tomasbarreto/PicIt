package com.example.picit.picdesc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.picit.utils.ScreenHeader
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.ui.theme.PicItTheme

@Composable
fun WaitingPhotoDescriptionScreen(
    onClickBackButton: ()->Unit = {}
){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var roomName = "Room name" // get in database from the room fields
        ScreenHeader(
            withBackButton = true,
            text = roomName,
            onClickBackButton = onClickBackButton
        )

        var timeForDescriptionRelease = "16:00" // get in database from the room fields
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.9f)
        ){
            Text(
                text ="Comeback at $timeForDescriptionRelease for the photo description release...",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 40.sp,
                lineHeight = 56.sp
            )

            Box(modifier = Modifier.align(Alignment.BottomEnd)){
                LeaderboardButton()
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaitingPhotoDescriptionScreen() {
    PicItTheme {
        WaitingPhotoDescriptionScreen()
    }
}