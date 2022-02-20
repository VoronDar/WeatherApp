package com.astery.weatherapp.ui.weatherToday

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.astery.weatherapp.model.pogo.Location
import com.google.android.gms.location.LocationServices
import timber.log.Timber

/** ask for permission if is it required and return location with a callback */
class LocationProvider(private var fragment: Fragment?) {

    fun getLocation(callback: LocationCallback) {
        if (isFragmentNull()) return

        if (ContextCompat.checkSelfPermission(
                fragment!!.activity!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_DENIED
        ) {
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(fragment!!.context!!)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Timber.d("current location = ${location.latitude}, ${location.longitude}")
                        callback.onSuccess(Location(location.latitude, location.longitude))
                    } else {
                        callback.onFailure()
                    }
                }
        } else {
            askLocationPermission(callback)
        }
    }

    private fun askLocationPermission(callback: LocationCallback) {
        if (isFragmentNull()) return

        try {
            val permReqLauncher =
                fragment!!.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isSuccess ->
                    if (isSuccess) {
                        getLocation(callback)
                    } else {
                        callback.onPermissionDenied()
                    }
                }

            permReqLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(
                fragment!!.activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

        } catch (e: Exception) {
            Timber.d("failed with message ${e.localizedMessage}")
            callback.onFailure()
        }
    }

    fun clearFragment() {
        fragment = null
    }

    private fun isFragmentNull(): Boolean {
        return if (fragment == null) {
            Timber.e("called location provider method after the fragment was destroyed")
            true
        } else false
    }

    interface LocationCallback {
        fun onSuccess(location: Location)
        fun onFailure()
        fun onPermissionDenied()
    }
}