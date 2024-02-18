package eu.tkacas.smartalert.viewmodel

import android.Manifest
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val switchStateCamera: MutableState<Boolean> = mutableStateOf(false)
    val switchStateCoarseLocation: MutableState<Boolean> = mutableStateOf(false)

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted) {
            when (permission) {
                Manifest.permission.CAMERA -> switchStateCamera.value = false
                Manifest.permission.ACCESS_COARSE_LOCATION -> switchStateCoarseLocation.value = false
            }
            if (!visiblePermissionDialogQueue.contains(permission)) {
                visiblePermissionDialogQueue.add(permission)
            }
        }
    }

}