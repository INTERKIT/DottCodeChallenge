package com.example.dottchallenge.common.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtils {

    private const val REQUEST_CODE_LOCATION = 55

    fun isLocationGranted(fragment: Fragment): Boolean {
        val permissionResult =
            ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val isGranted = permissionResult == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            fragment.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }

        return isGranted
    }
}