package com.example.picit.Timers

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.picit.entities.Time
import com.example.picit.utils.TimeLeftDisplay

@Composable
fun Timer(
    timeFor: String,
    viewModel: TimerViewModel,
    endingTime: Time,
    modifier: Modifier = Modifier
) {
    viewModel.startTimer(endingTime)
    var time = getTimeFromString(viewModel.getFormattedTimer())

    TimeLeftDisplay(timeFor = timeFor, hours = time.hours , mins = time.minutes , secs = time.second)
}

fun getTimeFromString(formattedTimer: String): Time {
    var time = formattedTimer.split(":")
    return Time(hours = time[0].toInt(), minutes = time[1].toInt(), second = time[2].toInt())
}