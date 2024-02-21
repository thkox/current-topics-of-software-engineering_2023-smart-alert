package eu.tkacas.smartalert.viewmodel.citizen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon

class AlertFormViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val selectedDangerLevelButton = mutableStateOf(1)
    val selectedWeatherPhenomenon = mutableStateOf(CriticalWeatherPhenomenon.EARTHQUAKE)
    val alertDescription = mutableStateOf("")

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

    fun setSelectedDangerLevelButton(level: Int){
        selectedDangerLevelButton.value = level
    }
    fun setSelectedWeatherPhenomenon(phenomenon: CriticalWeatherPhenomenon){
        selectedWeatherPhenomenon.value = phenomenon
    }

    fun setAlertDescription(description: String){
        alertDescription.value = description
    }

    fun sentAlert(){
        val selectedLevel = selectedDangerLevelButton.value
        val selectedPhenomenon = selectedWeatherPhenomenon.value
        val description = alertDescription.value
        // TODO send alert

    }
}