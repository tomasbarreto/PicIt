package com.example.picit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme

@Composable
fun FriendsListScreen(){
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
        AppBottomMenu(inFriendScreen = true, inHomeScreen = false, inProfileScreen = false)
    }
}

@Composable
fun FriendPreview(f: String) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
    ){
        Icon(Icons.Filled.AccountCircle, contentDescription = null, modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = f, fontSize = 24.sp)
        Row (modifier = Modifier
            .weight(1f),
            horizontalArrangement = Arrangement.End){
            Icon(
                Icons.Filled.Email,
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsListScreenPreview() {
    PicItTheme {
        FriendsListScreen()
    }
}