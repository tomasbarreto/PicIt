package com.example.picit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.picit.ui.theme.PicItTheme


@Composable
fun LeaderboardScreen(modifier : Modifier = Modifier){
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RoomHeader()
        AppBottomMenu(inFriendScreen = false, inHomeScreen = false, inProfileScreen = false)
    }

}




@Preview(showBackground = true)
@Composable
fun LeaderboardPreview() {
    PicItTheme {
        LeaderboardScreen()
    }
}