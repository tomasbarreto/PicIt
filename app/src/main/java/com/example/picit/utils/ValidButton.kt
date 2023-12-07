package com.example.picit.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ValidButton(){
    Button(
        onClick = { /*TODO*/ }
    ) {
        Icon(Icons.Rounded.Check, contentDescription = "verified", modifier = Modifier.size(40.dp))
    }
}