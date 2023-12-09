package com.example.picit.camera

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun CameraScreen(onClickBackButton: ()->Unit = {}) {

    if (allPermissionsGranted()) {
        CameraView(onClickBackButton)
    } else {
        ActivityCompat.requestPermissions(
            LocalContext.current as Activity, arrayOf(Manifest.permission.CAMERA), 10)
    }
}

@Composable
private fun allPermissionsGranted() = arrayOf(Manifest.permission.CAMERA).all {
    ContextCompat.checkSelfPermission(
        LocalContext.current, it) == PackageManager.PERMISSION_GRANTED
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(onClickBackButton: ()->Unit = {}) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember { LifecycleCameraController(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { CameraButtons(onClickBackButton) },
    ) { paddingValues : PaddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            factory = { context ->
           PreviewView(context).apply {
               layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
               scaleType = PreviewView.ScaleType.FILL_START
           }.also { previewView ->
            previewView.controller = cameraController
            cameraController.bindToLifecycle(lifecycleOwner)
           }

        })

    }
}

@Composable
fun CameraButtons(onClickBackButton: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Go Back") },
            onClick = onClickBackButton,
        )

        ExtendedFloatingActionButton(
            text = { Text(text = "Take Photo") },
            onClick = { /* Take Picture */ },
        )
    }
}
