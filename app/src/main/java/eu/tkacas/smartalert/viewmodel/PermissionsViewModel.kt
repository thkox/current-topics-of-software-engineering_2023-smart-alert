package eu.tkacas.smartalert.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel: ViewModel() {

    val permissions = mutableStateListOf<String>()

    fun dismissDialog() {
        permissions.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        granted: Boolean
    ) {
        if (granted) {
            permissions.add(0, permission)
        } else {
            permissions.remove(permission)
        }
    }

}