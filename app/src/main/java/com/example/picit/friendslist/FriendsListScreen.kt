package com.example.picit.friendslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.AppBottomMenu
import com.example.picit.utils.ScreenHeader

@Composable
fun FriendsListScreen(bottomNavigationsList: List<() -> Unit> = listOf({},{},{})){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(text = "Friends")
        Spacer(modifier = Modifier.height(60.dp))

        var friends = arrayOf("FriendName","FriendName","FriendName") // user objects
        Column {
            for (f in friends){
                FriendPreview(f)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { /*TODO: send friend requeset*/ }) {
            Text(text = "Add a friend", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
        AppBottomMenu(selectedItem = 0,  onClickForItems =bottomNavigationsList)
    }
}



@Preview(showBackground = true)
@Composable
fun FriendsListScreenPreview() {
    PicItTheme {
        FriendsListScreen()
    }
}