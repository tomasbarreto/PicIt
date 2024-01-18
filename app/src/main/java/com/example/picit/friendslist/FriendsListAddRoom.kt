package com.example.picit.friendslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun FriendsListAddRoomScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(withBackButton = true, text = "Friends")
        Spacer(modifier = Modifier.height(60.dp))

        var friends = arrayOf("FriendName","FriendName","FriendName","FriendName","FriendName","FriendName","FriendName","FriendName","FriendName") // user objects
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).height(600.dp)) {
            for (f in friends){
                FriendPreview(f, true)
            }
        }

        Button(onClick = { /*TODO: send friend requeset*/ }) {
            Text(text = "Invite Friend", fontSize = 24.sp)
        }
    }
}

@Composable
fun addButton() {
    var selected by remember { mutableStateOf(false) }

    Icon(
        imageVector = if (!selected) Icons.Filled.Add else Icons.Filled.Check,
        contentDescription = null,
        modifier = Modifier
            .size(44.dp)
            .clickable { selected = !selected }
    )
}

@Preview(showBackground = true)
@Composable
fun FriendsListAddRoomScreenPreview() {
    PicItTheme {
        FriendsListAddRoomScreen()
    }
}