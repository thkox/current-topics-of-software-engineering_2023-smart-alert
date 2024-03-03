package eu.tkacas.smartalert.viewmodel.employee

import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.database.cloud.CloudFunctionsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsByLocationViewModel: ViewModel() {

    private var _cloudFunctionsUtils: CloudFunctionsUtils = CloudFunctionsUtils()
    fun deleteAllEventsByPhenomenonAndLocation(phenomenon: String, locationID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _cloudFunctionsUtils.deleteAlertsByPhenomenonAndLocation(phenomenon, locationID)
        }
    }

    fun deleteEventByPhenomenonAndLocation(phenomenon: String, locationID: String, alertID:String) {
        CoroutineScope(Dispatchers.IO).launch {
            _cloudFunctionsUtils.deleteAlertByPhenomenonAndLocation(phenomenon, locationID, alertID)
        }
    }
}