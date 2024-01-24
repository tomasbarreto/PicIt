package com.example.picit.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var isOver by mutableStateOf(false)

    private var second = 1000L
    private var reloaded = false

    fun startTimer(finalTime: Time, reload: ()->Unit={}) {
        viewModelScope.launch {
            var finalTimeMillis = getTimeMillis(finalTime)
            var now = LocalDateTime.now()
            var currentMillis = getTimeMillis(Time(hours = now.hour, minutes = now.minute, second = now.second))

            var timeLeftMillis = 0L

            if (finalTimeMillis - currentMillis >= 0)
                timeLeftMillis = finalTimeMillis - currentMillis
            else {
                var millisToMidNight = getTimeMillis(Time(hours = 24, minutes = 0, second = 0)) - currentMillis
                timeLeftMillis = millisToMidNight + finalTimeMillis
            }

            while (!isOver) {
                delay(second)

                timeLeftMillis -= 1000

                formattedTime = formatTime(timeLeftMillis)

                if (timeLeftMillis <= 0)
                    isOver = true
            }
            if(!reloaded){
                reload()
                reloaded=true
            }
        }
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )

        val formatter = DateTimeFormatter.ofPattern(
            "HH:mm:ss",
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

    fun cancelTimer() {
        isOver = true
    }
}