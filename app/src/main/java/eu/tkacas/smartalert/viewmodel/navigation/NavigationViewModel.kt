package eu.tkacas.smartalert.viewmodel.navigation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.cloud.CloudFunctionsUtils
import eu.tkacas.smartalert.cloud.userExists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationViewModel(context:Context): ViewModel() {

    var cloudFunctionsUtils: CloudFunctionsUtils = CloudFunctionsUtils()
    private var sharedPrefManager: SharedPrefManager = SharedPrefManager(context)

    fun findStartDestination(): String = if (userExists()) { "home" } else {"welcome" }

    fun setUserIdentity(){
        CoroutineScope(Dispatchers.IO).launch {
            val isEmployee = cloudFunctionsUtils.userIsEmployee()
            sharedPrefManager.setIsEmployee(isEmployee)
        }
    }

    @Composable
    fun permissionsAreGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}