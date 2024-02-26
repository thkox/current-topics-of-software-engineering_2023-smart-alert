package eu.tkacas.smartalert.viewmodel.employee

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

class EventsByLocationViewModel: ViewModel() {

    var cloudFunctionsUtils: CloudFunctionsUtils = CloudFunctionsUtils()
    fun deleteAllEventsByPhenomenonAndLocation(phenomenon: String, location: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cloudFunctionsUtils.deleteAlertsByPhenomenonAndLocation(phenomenon, location)
        }
    }

    fun deleteEventByPhenomenonAndLocation(phenomenon: String, location: String, alertID:String) {
        CoroutineScope(Dispatchers.IO).launch {
            cloudFunctionsUtils.deleteAlertByPhenomenonAndLocation(phenomenon, location, alertID)
        }
    }
}