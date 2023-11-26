package com.example.picit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BackButton() {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.seta_esquerda),
                contentDescription = "back arrow",
                modifier = Modifier.width(20.dp))
        }
    }
}