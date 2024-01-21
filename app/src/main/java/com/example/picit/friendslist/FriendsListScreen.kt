package com.example.picit.friendslist

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.example.picit.entities.User
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.AppBottomMenu
import com.example.picit.utils.ScreenHeader

@Composable
fun FriendsListScreen(
    addToRoom: Boolean = false,
    bottomNavigationsList: List<() -> Unit> = listOf({},{},{}),
    onClickBackButton: () -> Unit={},
    usersToInvite: List<User> = emptyList()
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(withBackButton = addToRoom, withAddUsers = addToRoom, text = "Friends", onClickBackButton = onClickBackButton)
        Spacer(modifier = Modifier.height(60.dp))

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .height(500.dp)) {
            usersToInvite.forEach { user ->
                FriendPreview(user.username)
            }
        }
        if (!addToRoom) {
            Spacer(modifier = Modifier.weight(1f))
        }
        Button(onClick = { /*TODO: send friend requeset*/ }) {
            Text(text = "Add friend", fontSize = 24.sp)
        }
        if (!addToRoom) {
            Spacer(modifier = Modifier.height(32.dp))
            AppBottomMenu(selectedItem = 0,  onClickForItems =bottomNavigationsList)
        }
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
            addButton()
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
fun FriendsListScreenPreview() {
    PicItTheme {
        FriendsListScreen()
    }
}