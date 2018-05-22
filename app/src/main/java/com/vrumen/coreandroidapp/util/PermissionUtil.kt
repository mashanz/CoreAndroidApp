package com.bilinedev.ikasmariagitma.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import com.bilinedev.ikasmariagitma.ui.dialog.MessageDialog

/**
 * Created by Chandra on 7/14/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

object PermissionUtil {

    fun isCameraGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                val dialog = DialogUtil.showMessage(fragmentManager,
                        "Aplikasi membutuhkan akses kamera dan galeri")
                dialog.isCancelable = false
                dialog.setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                    override fun onClosed() = requestCameraPermission(activity, requestCode)
                })
            } else requestCameraPermission(activity, requestCode)
            return false
        }
    }

    fun requestCameraPermission(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)

    fun isLocationGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean =
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    DialogUtil.showMessage(fragmentManager, "Aplikasi membutuhkan akses lokasi anda")
                            .setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                                override fun onClosed() = requestLocationPermission(activity, requestCode)
                            })
                } else requestLocationPermission(activity, requestCode)
                false
            }

    private fun requestLocationPermission(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), requestCode)


    fun isStorageGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean =
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    val dialog = DialogUtil.showMessage(fragmentManager,
                            "Aplikasi membutuhkan akses galeri")
                    dialog.isCancelable = false
                    dialog.setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                        override fun onClosed() = requestStoragePermission(activity, requestCode)
                    })
                } else requestStoragePermission(activity, requestCode)
                false
            }

    fun requestStoragePermission(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)

    fun isCallGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean =
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
                    val dialog = DialogUtil.showMessage(fragmentManager,
                            "Aplikasi membutuhkan akses Telepon")
                    dialog.isCancelable = false
                    dialog.setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                        override fun onClosed() = requestCallPermission(activity, requestCode)
                    })
                } else requestCallPermission(activity, requestCode)
                false
            }

    private fun requestCallPermission(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), requestCode)

    fun isSMSGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECEIVE_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                val dialog = DialogUtil.showMessage(fragmentManager,
                        "Aplikasi membutuhkan akses SMS")
                dialog.isCancelable = false
                dialog.setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                    override fun onClosed() = requestSMSPermission(activity, requestCode)
                })

            } else requestSMSPermission(activity, requestCode)
            return false
        }
    }

    private fun requestSMSPermission(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE), requestCode)

    fun isReadContactGranted(activity: Activity, fragmentManager: FragmentManager, requestCode: Int): Boolean =
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                    val dialog = DialogUtil.showMessage(fragmentManager, "Aplikasi membutuhkan akses membaca daftar kontak anda")
                    dialog.isCancelable = false
                    dialog.setOnMessageClosed(object : MessageDialog.OnMessageClosed {
                        override fun onClosed() = requestReadContacts(activity, requestCode)
                    })
                } else requestReadContacts(activity, requestCode)
                false
            }

    private fun requestReadContacts(activity: Activity, requestCode: Int) =
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CONTACTS), requestCode)

    fun handleResult(context: Context?, permissions: Array<out String>, grantResults: IntArray, permissionListener: PermissionListener) = if (grantResults.isNotEmpty()) {
        var isGrant = true
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) isGrant = false
        }

        if (isGrant) permissionListener.onGranted()
        else if (!ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permissions[0]))
            permissionListener.onNeverAsk()
        else permissionListener.onDenied()
    } else permissionListener.onDenied()

    interface PermissionListener {
        fun onGranted()
        fun onDenied()
        fun onNeverAsk()
    }
}
