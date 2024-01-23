package com.example.picit.utils

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.picit.ui.theme.PicItTheme

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.width(64.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    PicItTheme {
        LoadingIndicator()
    }
}