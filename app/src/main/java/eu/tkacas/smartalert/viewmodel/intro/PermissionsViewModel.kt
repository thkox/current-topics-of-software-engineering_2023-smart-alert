package eu.tkacas.smartalert.viewmodel.intro

import android.Manifest
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private val _switchStateCoarseLocation: MutableState<Boolean> = mutableStateOf(false)


    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted) {
            when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> _switchStateCoarseLocation.value = false
            }
            if (!visiblePermissionDialogQueue.contains(permission)) {
                visiblePermissionDialogQueue.add(permission)
            }
        }
    }

}