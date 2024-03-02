package eu.tkacas.smartalert.viewmodel.employee

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.cloud.storageRef
import eu.tkacas.smartalert.interfacesAPI.PlacesAPI
import eu.tkacas.smartalert.models.Alert
import eu.tkacas.smartalert.models.Bounds
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.EmergencyLevel
import eu.tkacas.smartalert.models.LatLng
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlertCitizensFormViewModel(context: Context): ViewModel() {

    val sharedPrefManager = SharedPrefManager(context)

    val selectedArea = mutableStateOf("")
    val selectedWeatherPhenomenon = mutableStateOf(CriticalWeatherPhenomenon.EARTHQUAKE)
    val selectedDangerLevelButton = mutableStateOf(EmergencyLevel.LOW)

    fun setSelectedArea(area: String){
        selectedArea.value = area
    }

    fun setSelectedWeatherPhenomenon(phenomenon: CriticalWeatherPhenomenon){
        selectedWeatherPhenomenon.value = phenomenon
    }

    fun setSelectedDangerLevelButton(level: EmergencyLevel){
        selectedDangerLevelButton.value = level
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

    private fun getBounds(): Bounds {
        return Bounds(
            LatLng(
                sharedPrefManager.getBoundsNorthEastLat(),
                sharedPrefManager.getBoundsNorthEastLng()
            ), LatLng(
                sharedPrefManager.getBoundsSouthWestLat(),
                sharedPrefManager.getBoundsSouthWestLng()
            )
        )

    }

    suspend fun sendAlertToCitizens() {
        val selectedLocation = if (selectedArea.value.isEmpty()){
            sharedPrefManager.getLocationName()
        } else {
            selectedArea.value
        }

        val selectedWeatherPhenomenon = selectedWeatherPhenomenon.value
        val selectedDangerLevelButton = selectedDangerLevelButton.value

        val database = storageRef()

        // Create a reference to the "notificationsToCitizens" node
        val myRef = database.getReference("notificationsToCitizens")

        // Create a unique key for the new alert
        val key = myRef.push().key

        // Get the bounds from shared preferences
        val bounds = getBounds()

        // Create am instance of alert
        val alert = Alert(
            locationName = selectedLocation,
            locationBounds = bounds,
            criticalWeatherPhenomenon = selectedWeatherPhenomenon,
            criticalLevel = selectedDangerLevelButton
        )

        // Send the data to the server
        if (key != null) {
            myRef.child(key).setValue(alert)
        }
    }


}