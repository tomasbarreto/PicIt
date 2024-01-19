package com.example.picit.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationClient {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun startLocationClient(context: Context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLocation(context: Context): String {
        var result = ""

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==  PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                val geoCoder =  Geocoder(context)
                val currentLocation = geoCoder.getFromLocation(location.latitude, location.longitude, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        result = addresses.first().locality + ", " + addresses.first().countryName
                    }
                })
            }

        }

        return result
    }

    fun isLocationPermGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==  PackageManager.PERMISSION_GRANTED
    }
}