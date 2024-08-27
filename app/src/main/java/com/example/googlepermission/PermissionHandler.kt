package com.example.googlepermission

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler(
    private val activity: AppCompatActivity,
    private val permission: String,
    private val requestCode: Int,
    private val permissionName: String,
    private val onPermissionGranted: () -> Unit,
    private val onPermissionDenied: () -> Unit
) {

    fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            activity.shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationale()
            }
            else -> {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        }
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(activity).apply {
            setMessage("Permission needed for $permissionName.")
            setTitle("Permission Needed")
            setPositiveButton("Allow") { dialogInterface, _ ->
                dialogInterface.dismiss()
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
            setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
                onPermissionDenied()
            }
            show()
        }
    }

    fun handleRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}
