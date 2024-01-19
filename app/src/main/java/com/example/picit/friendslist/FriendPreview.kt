package com.example.picit.friendslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FriendPreview(f: String, addFriendToRoom: Boolean = false,
                  selectFriend: () -> Unit = {}, deselectFriend: () -> Unit = {}) {
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
            if(addFriendToRoom) {
                addButton(selectFriend, deselectFriend)
            } else {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                )
            }

        }
    }
}