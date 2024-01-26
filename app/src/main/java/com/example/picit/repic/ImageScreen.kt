package com.example.picit.repic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.LoadingIndicator
import com.example.picit.utils.ScreenHeader

@Composable
fun ImageScreen(
    onClickBackButton: ()->Unit = {},
    imageUrl: String =""
    ) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenHeader(text = "", withBackButton = true, onClickBackButton = { onClickBackButton() })

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = "",
                loading = { LoadingIndicator() }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    PicItTheme {
        ImageScreen()
    }
}