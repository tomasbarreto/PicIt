package com.example.picit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.picit.ui.theme.PicItTheme


@Composable
fun LeaderboardScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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