package com.example.picit.camera

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Camera2(getImageUri: (Uri, Context) -> Unit = {_,_ ->}) {
    var context = LocalContext.current

//    val file = context.createImageFile()
//
//    val uri = FileProvider.getUriForFile(
//        Objects.requireNonNull(context),
//        context.packageName + ".provider", file
//    )

//    val cameraLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
//            getImageUri(uri, context)
//
//        }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) {
//        if (it) {
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//            cameraLauncher.launch(uri)
//        } else {
//            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(onClick = {
//            val permissionCheckResult =
//                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//                cameraLauncher.launch(uri)
//            } else {
//                // Request a permission
//                permissionLauncher.launch(Manifest.permission.CAMERA)
//            }
//        }) {
//            Text(text = "Capture Image From Camera")
//        }
//    }
}

