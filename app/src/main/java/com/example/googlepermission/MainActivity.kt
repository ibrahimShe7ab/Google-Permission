

package com.example.googlepermission

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.googlepermission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val LOCATION_CODE = 101
    private val CAMERA_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val locationPermissionHandler = PermissionHandler(
                activity = this,
                permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
                requestCode = LOCATION_CODE,
                permissionName = "location",
                onPermissionGranted = {
                    Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show()
                    // TODO: Get user location
                },
                onPermissionDenied = {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                }
            )
            locationPermissionHandler.checkPermission()
        }

        binding.camera.setOnClickListener {
            val cameraPermissionHandler = PermissionHandler(
                activity = this,
                permission = android.Manifest.permission.CAMERA,
                requestCode = CAMERA_CODE,
                permissionName = "camera",
                onPermissionGranted = {
                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show()
                    // TODO: Access camera
                },
                onPermissionDenied = {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
            )
            cameraPermissionHandler.checkPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_CODE, CAMERA_CODE -> {
                val permissionName = when (requestCode) {
                    LOCATION_CODE -> "location"
                    CAMERA_CODE -> "camera"
                    else -> "unknown"
                }

                val permission = when (requestCode) {
                    LOCATION_CODE -> android.Manifest.permission.ACCESS_FINE_LOCATION
                    CAMERA_CODE -> android.Manifest.permission.CAMERA
                    else -> null
                }

                permission?.let {
                    PermissionHandler(
                        activity = this,
                        permission = it,
                        requestCode = requestCode,
                        permissionName = permissionName,
                        onPermissionGranted = {
                            Toast.makeText(this, "$permissionName permission granted", Toast.LENGTH_LONG).show()
                            // TODO: Handle permission granted case
                        },
                        onPermissionDenied = {
                            Toast.makeText(this, "$permissionName permission denied", Toast.LENGTH_SHORT).show()
                        }
                    ).handleRequestPermissionsResult(requestCode, grantResults)
                }
            }
        }
    }
}
