package com.example.picit.createroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTime(modifier: Modifier = Modifier) {
    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }

    var maxHours = 23
    var maxMinutes = 59

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = hours,
            onValueChange = { if (it == "") { hours = "" } else { if (it.toInt() <= maxHours) { if (it.length <= 2) { hours = it } } else {
                hours =
                    maxHours.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )

        Text(text = " : ", fontSize = 32.sp, modifier = Modifier.padding(vertical = 5.dp))

        TextField(
            value = minutes,
            onValueChange = { if (it == "") { minutes = "" } else { if (it.toInt() <= maxMinutes) { if (it.length <= 2) { minutes = it }} else {
                minutes =
                    maxMinutes.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )
    }
}