package com.example.picit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomMenu(inFriendScreen: Boolean, inHomeScreen: Boolean,  inProfileScreen: Boolean) {
    Divider(color = Color.Black, thickness = 1.dp)
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ){
        if(inFriendScreen){
            Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier.size(50.dp))
        }
        else{
            Icon(Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(50.dp))
        }

        if(inHomeScreen){
            Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(50.dp))
        }
        else{
            Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(50.dp))
        }

        if(inProfileScreen){
            Icon(Icons.Filled.AccountCircle, contentDescription = null, modifier = Modifier.size(50.dp))
        }
        else{
            Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(50.dp))
        }
    }
}