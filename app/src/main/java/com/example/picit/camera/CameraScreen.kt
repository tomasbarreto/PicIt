package com.example.picit.camera

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
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
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private val TAG:String ="CameraScreen"

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

private lateinit var cameraExecutor: Executor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(onClickBackButton: ()->Unit = {}) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember { LifecycleCameraController(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    cameraExecutor = Executors.newSingleThreadExecutor()
    val onClickTakePhotoButton = {
        val file = File.createTempFile("img",".jpg")
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(outputFileOptions,cameraExecutor,
            object: ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d(TAG, "URI: ${outputFileResults.savedUri}")
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, "Error on taking picdesc photo: $exception")
                }

            })
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { CameraButtons(onClickBackButton, onClickTakePhotoButton) },
    ) { paddingValues : PaddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            factory = { context ->
           val previewView = PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_START
                }

                val previewUseCase = Preview.Builder().build()
                previewUseCase.setSurfaceProvider((previewView.surfaceProvider))

                val listenableFuture = ProcessCameraProvider.getInstance(context)
                listenableFuture.addListener({
                    val cameraProvider=listenableFuture.get()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        previewUseCase, imageCapture)
                }, ContextCompat.getMainExecutor(context))
                previewView



        })

    }
}

@Composable
fun CameraButtons(
    onClickBackButton: () -> Unit,
    onClickTakePhotoButton: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Go Back") },
            onClick = onClickBackButton,
        )

        ExtendedFloatingActionButton(
            text = { Text(text = "Take Photo") },
            onClick = { onClickTakePhotoButton() },
        )
    }
}
