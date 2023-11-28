package com.example.picit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PromptDisplay(prompt: String) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .height(50.dp)
        .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically)
    {
        Column {
            Text(text = "Prompt:", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.padding(start=5.dp)) {
            Text(text = prompt, fontSize = 20.sp)
        }
    }

}