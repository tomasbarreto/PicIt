package com.example.picit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.picit.navigation.PicItNavHost
import com.example.picit.ui.theme.PicItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicItTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isLocationPermGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                    if (!isLocationPermGranted) {
                        val locationPermissionResultLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestPermission(),
                            onResult = { isGranted ->
                                isLocationPermGranted = isGranted
                            }
                        )

                        SideEffect {
                            locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        }
                    }

                    PicItApp()
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicItApp() {
    // A surface container using the 'background' color from the theme
    val navController = rememberNavController()
    Scaffold {


        PicItNavHost(navController = navController, modifier=Modifier.padding(it))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicItTheme {
//        UserRoomsScreen()
    }
}
