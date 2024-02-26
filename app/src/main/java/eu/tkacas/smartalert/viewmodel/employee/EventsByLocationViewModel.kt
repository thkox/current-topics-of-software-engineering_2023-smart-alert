package eu.tkacas.smartalert.viewmodel.employee

import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.cloud.CloudFunctionsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsByLocationViewModel: ViewModel() {

    private var _cloudFunctionsUtils: CloudFunctionsUtils = CloudFunctionsUtils()
    fun deleteAllEventsByPhenomenonAndLocation(phenomenon: String, location: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _cloudFunctionsUtils.deleteAlertsByPhenomenonAndLocation(phenomenon, location)
        }
    }

    fun deleteEventByPhenomenonAndLocation(phenomenon: String, location: String, alertID:String) {
        CoroutineScope(Dispatchers.IO).launch {
            _cloudFunctionsUtils.deleteAlertByPhenomenonAndLocation(phenomenon, location, alertID)
        }
    }
}