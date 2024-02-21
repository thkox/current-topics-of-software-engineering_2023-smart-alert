package eu.tkacas.smartalert.viewmodel.citizen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class AlertFormViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if(!isGranted){
            if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
                visiblePermissionDialogQueue.add(permission)
            }
        }
    }
}