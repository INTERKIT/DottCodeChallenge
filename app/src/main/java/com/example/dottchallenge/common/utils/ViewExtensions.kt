package com.example.dottchallenge.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.dottchallenge.R

fun Activity.hideKeyboard() {
    currentFocus?.hideKeyboard()
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.showPermissionDialog() {
    AlertDialog.Builder(requireContext())
        .setPositiveButton(R.string.action_settings) { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        .setNegativeButton(R.string.action_close) { dialog, _ ->
            dialog.dismiss()
            requireActivity().finish()
        }
        .setTitle(getString(R.string.maps_location_permission))
        .create()
        .show()
}