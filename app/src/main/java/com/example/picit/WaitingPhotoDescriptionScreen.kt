package com.example.picit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme

@Composable
fun WaitingPhotoDescriptionScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var roomName = "Room name" // get in database from the room fields
        ScreenHeader(text = roomName, withButton = true)

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
        AppBottomMenu(inFriendScreen = false, inHomeScreen = true, inProfileScreen = false)
    }
}

@Composable
fun LeaderboardButton() {
    Button(
        onClick = { /*TODO*/ },
    ) {
        Icon(Icons.Outlined.Star, contentDescription = null,)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWaitingPhotoDescriptionScreen() {
    PicItTheme {
        WaitingPhotoDescriptionScreen()
    }
}