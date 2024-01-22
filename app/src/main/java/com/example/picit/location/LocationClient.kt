package com.example.picit.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

private val TAG = "LocationClient"

class LocationClient {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun startLocationClient(context: Context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    fun getLocation(context: Context,getLocation: (String)->Unit={}): String {
        fusedLocationProviderClient.flushLocations()

        var result = ""

        var lat = 0.0
        var lon = 0.0

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(listener: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested(): Boolean {
                return false
            }
        })
            .addOnSuccessListener {
                if (it != null) {
                    lat = it.latitude
                    lon = it.longitude

                    val geoCoder =  Geocoder(context)

                    val currentLocation =
                        geoCoder.getFromLocation(lat, lon, 1)

                    if (currentLocation != null) {
                        result = currentLocation[0].locality + ", " + currentLocation[0].countryName
                        getLocation(result)
                        Log.d(
                            "Location",
                            currentLocation[0].locality + ", " + currentLocation[0].countryName
                        )
                    }
                }

            }



        /*
        if (isLocationPermGranted(context)) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                val geoCoder =  Geocoder(context)

                if(location != null) {
                    val currentLocation =
                        geoCoder.getFromLocation(location.latitude, location.longitude, 1)

                    if (currentLocation != null) {
                        Log.d(
                            "Location",
                            currentLocation[0].locality + ", " + currentLocation[0].countryName
                        )
                    }
                }
            }

        }
        */

        return result
    }

    fun isLocationPermGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==  PackageManager.PERMISSION_GRANTED
    }
}