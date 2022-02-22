package com.astery.weatherapp.ui.fragments.weatherToday

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.astery.weatherapp.model.pogo.Location
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import android.location.LocationManager
import androidx.core.content.getSystemService


/** ask for permission if is it required and return location with a callback */
class LocationProvider(private var fragment: Fragment?) {

    lateinit var permReqLauncher: ActivityResultLauncher<String>
    private lateinit var callback: LocationCallback

    fun getLocation(callback: LocationCallback) {
        if (isFragmentNull()) return

        this.callback = callback

        if (ContextCompat.checkSelfPermission(
                fragment!!.activity!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_DENIED
        ) {

            if (isFragmentNull()) return

            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(fragment!!.context!!)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        IS_PERMISSION_PROVIDED = true
                        callback.onSuccess(Location(location.latitude, location.longitude))
                    } else {
                        Timber.d("can't find location")
                        callback.onError()
                    }
                }
        } else {
            askLocationPermission()
        }
    }

    private fun askLocationPermission() {
        if (isFragmentNull()) return
        try {
            permReqLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(
                fragment!!.activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

        } catch (e: Exception) {
            Timber.d("failed with message ${e.localizedMessage}")
            callback.onError()
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

    fun registerForActivityResult() {
        permReqLauncher =
            fragment!!.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isSuccess ->
                if (isSuccess) {
                    getLocation(callback)
                } else {
                    callback.onPermissionDenied()
                }
            }
    }


    interface LocationCallback {
        fun onSuccess(location: Location)
        fun onError() { this.onPermissionDenied() }
        fun onPermissionDenied()
    }

    companion object {
        var IS_PERMISSION_PROVIDED = false
            private set
    }
}