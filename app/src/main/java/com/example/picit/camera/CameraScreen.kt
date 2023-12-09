package com.example.picit.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.picit.CameraScreen1
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun CameraScreen() {

    if (allPermissionsGranted()) {
        CameraView()
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
fun CameraView() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember { LifecycleCameraController(context) }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues : PaddingValues ->
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
