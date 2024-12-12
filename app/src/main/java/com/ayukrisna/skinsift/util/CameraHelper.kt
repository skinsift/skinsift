package com.ayukrisna.skinsift.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

// NOT USED
class CameraHelper(private val context: Context, private val authority: String) {
    private val directory = context.getExternalFilesDir("pictures")

    fun getTempUri(): Uri? {
        return try {
            if (directory == null) {
                Toast.makeText(context, "Directory is not initialized.", Toast.LENGTH_SHORT).show()
                return null
            }

            if (!directory.exists() && !directory.mkdirs()) {
                Toast.makeText(context, "Failed to create directory.", Toast.LENGTH_SHORT).show()
                return null
            }

            val file = File.createTempFile("image_${System.currentTimeMillis()}", ".jpg", directory)
            FileProvider.getUriForFile(context, authority, file)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to create temp file for photo.", Toast.LENGTH_SHORT).show()
            null
        }
    }

    fun requestCameraPermission(
        cameraPermissionLauncher: ActivityResultLauncher<String>,
        onPermissionGranted: () -> Unit
    ) {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            cameraPermissionLauncher.launch(permission)
        }
    }
}