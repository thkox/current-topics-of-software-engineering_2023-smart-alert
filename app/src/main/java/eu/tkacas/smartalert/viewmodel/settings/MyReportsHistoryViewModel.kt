package eu.tkacas.smartalert.viewmodel.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tkacas.smartalert.cloud.CloudFunctionsUtils
import eu.tkacas.smartalert.cloud.getAlertFormsByUser
import eu.tkacas.smartalert.models.CitizenMessage2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyReportsHistoryViewModel: ViewModel() {

    // Define a LiveData property to hold the list of alerts
    val alerts: MutableLiveData<List<CitizenMessage2>> = fetchAlerts()

    private fun fetchAlerts(): MutableLiveData<List<CitizenMessage2>> {
        val alerts = MutableLiveData<List<CitizenMessage2>>()
        CoroutineScope(Dispatchers.IO).launch {
            getAlertFormsByUser { success, data, error ->
                if (success) {
                    // Handle the data here. This is a list of CitizenMessage2 objects.
                    data?.let {
                        alerts.postValue(it) // Update alerts LiveData on the main thread
                    }
                } else {
                    // Handle the error here. This is a string containing the error message.
                    println("Error: $error")
                }
            }
        }
        return alerts
    }
}