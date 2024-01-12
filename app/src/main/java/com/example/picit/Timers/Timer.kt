package com.example.picit.Timers

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.picit.entities.Time

@Composable
fun Timer(
    viewModel: TimerViewModel,
    endingTime: Time,
    modifier: Modifier = Modifier
) {
    viewModel.startTimer(endingTime)

    Box {
        Text(text = viewModel.getFormattedTime())
    }
}