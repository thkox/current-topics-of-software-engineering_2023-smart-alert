package eu.tkacas.smartalert.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import eu.tkacas.smartalert.models.LocationData
import kotlinx.coroutines.tasks.await

class LocationViewModel(val context: Context): ViewModel() {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    suspend fun getLastLocation(): LocationData? {
        return if (hasLocationPermission(context)) {
            try {
                fusedLocationClient.lastLocation.await()?.let {
                    LocationData(it.latitude, it.longitude)
                }
            } catch (e: SecurityException) {
                // Handle the case where the permission is not granted
                print("Location permission not granted.")
                null
            }
        } else {
            null
        }
    }

    fun hasLocationPermission(context: Context):Boolean{
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}