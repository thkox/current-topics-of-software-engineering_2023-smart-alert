package eu.tkacas.smartalert.viewmodel.employee

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.cloud.getUserID
import eu.tkacas.smartalert.cloud.storageRef
import eu.tkacas.smartalert.interfacesAPI.PlacesAPI
import eu.tkacas.smartalert.models.Alert
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlertCitizensFormViewModel(): ViewModel() {

    val selectedArea = mutableStateOf("")
    val selectedWeatherPhenomenon = mutableStateOf(CriticalWeatherPhenomenon.EARTHQUAKE)
    val selectedDangerLevelButton = mutableIntStateOf(1)

    fun setSelectedArea(area: String){
        selectedArea.value = area
    }

    fun setSelectedWeatherPhenomenon(phenomenon: CriticalWeatherPhenomenon){
        selectedWeatherPhenomenon.value = phenomenon
    }

    fun setSelectedDangerLevelButton(level: Int){
        selectedDangerLevelButton.intValue = level
    }
    private val retrofit: Retrofit
        get() = setupRetrofit()

    private fun setupRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/") // Base URL for Google Places API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createPlacesAPI(): PlacesAPI {
        return retrofit.create(PlacesAPI::class.java)
    }

    suspend fun sendAlertToCitizens() {
        val selectedArea = selectedArea.value
        val selectedWeatherPhenomenon = selectedWeatherPhenomenon.value
        val selectedDangerLevelButton = selectedDangerLevelButton.value

        val database = storageRef()

        // Create a reference to the "notificationsToCitizens" node
        val myRef = database.getReference("notificationsToCitizens")

        // Create a unique key for the new alert
        val key = myRef.push().key

        // Create am instance of alert
        val alert = Alert(
            area = selectedArea,
            criticalWeatherPhenomenon = selectedWeatherPhenomenon,
            criticalLevel = selectedDangerLevelButton
        )

        // Send the data to the server
        if (key != null) {
            myRef.child(key).setValue(alert)
        }
    }


}