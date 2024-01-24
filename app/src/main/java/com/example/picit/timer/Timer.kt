package com.example.picit.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.picit.entities.Time
import com.example.picit.utils.TimeLeftDisplay

@Composable
fun Timer(
    timeFor: String,
    viewModel: TimerViewModel,
    endingTime: Time,
    reload: ()->Unit = {},
    modifier: Modifier = Modifier
) {

    DisposableEffect(endingTime) {
        viewModel.startTimer(endingTime, reload)

        onDispose {
            viewModel.cancelTimer()
        }
    }
    var time = getTimeFromString(viewModel.getFormattedTimer())

    TimeLeftDisplay(timeFor = timeFor, hours = time.hours , mins = time.minutes , secs = time.second)
}

fun getTimeFromString(formattedTimer: String): Time {
    var time = formattedTimer.split(":")
    return Time(hours = time[0].toInt(), minutes = time[1].toInt(), second = time[2].toInt())
}