package com.example.picit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.picit.ui.theme.PicItTheme

@Composable
fun FriendsListScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(text = "Friends")
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsListScreenPreview() {
    PicItTheme {
        FriendsListScreen()
    }
}