package com.example.picit.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.Time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TimerViewModel: ViewModel() {

    private var formattedTime by mutableStateOf("00:00:00")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private var finalTimeMillis = 0L

    private var timeLeftMillis = 0L

    private var isOver by mutableStateOf(false)

    private var second = 1000L

    fun startTimer(finalTime: Time) {
        coroutineScope.launch {
            finalTimeMillis = getTimeMillis(finalTime)

            while (!isOver) {
                delay(second)

                var now = LocalDateTime.now()
                timeLeftMillis = finalTimeMillis - getTimeMillis(Time(hours = now.hour, minutes = now.minute, second = now.second))

                print(finalTimeMillis)
                print(System.currentTimeMillis())

                formattedTime = formatTime(timeLeftMillis)

                if (finalTimeMillis <= 0)
                    isOver = true
            }
        }
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )

        val formatter = DateTimeFormatter.ofPattern(
            "hh:mm:ss",
            Locale.getDefault()
        )

        return localDateTime.format(formatter)
    }

    private fun getTimeMillis(finalTime: Time): Long {
        return finalTime.hours * 60 * 60 * 1000L + finalTime.minutes * 60 * 1000L + finalTime.second * 1000L
    }

    fun getFormattedTimer(): String {
        return formattedTime
    }
}