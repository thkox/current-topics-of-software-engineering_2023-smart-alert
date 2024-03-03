package eu.tkacas.smartalert.viewmodel.employee

import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.database.cloud.CloudFunctionsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    fun isWithinLastHour(timeStamp: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = LocalTime.parse(timeStamp, formatter)
        return time.isAfter(LocalTime.now().minusHours(1))
    }
}