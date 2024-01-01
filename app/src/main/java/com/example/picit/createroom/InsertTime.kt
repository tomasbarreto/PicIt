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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTime(modifier: Modifier = Modifier, hours: MutableState<String>, minutes: MutableState<String>) {

    var maxHours = 23
    var maxMinutes = 59
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = hours.value,
            onValueChange = { if (it == "") { hours.value = "" } else { if (it.toInt() <= maxHours) { if (it.length <= 2) { hours.value = it } } else {
                hours.value =
                    maxHours.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )

        Text(text = " : ", fontSize = 32.sp, modifier = Modifier.padding(vertical = 5.dp))

        TextField(
            value = minutes.value,
            onValueChange = { if (it == "") { minutes.value = "" } else { if (it.toInt() <= maxMinutes) { if (it.length <= 2) { minutes.value = it }} else {
                minutes.value =
                    maxMinutes.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )
    }
}