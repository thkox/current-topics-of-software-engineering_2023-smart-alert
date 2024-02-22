package eu.tkacas.smartalert.viewmodel.citizen

import android.content.Context
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import eu.tkacas.smartalert.models.CitizenMessage
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.LocationData
import com.google.gson.Gson
import eu.tkacas.smartalert.viewmodel.LocationViewModel

class AlertFormViewModel(context: Context): ViewModel() {

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val selectedDangerLevelButton = mutableStateOf(1)
    val selectedWeatherPhenomenon = mutableStateOf(CriticalWeatherPhenomenon.EARTHQUAKE)
    val alertDescription = mutableStateOf("")
    var locationData = LocationData(0.0, 0.0)
    val photoURL = mutableStateOf("")

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

    fun setAlertDescription(description: String?){
        if (description != null) {
            alertDescription.value = description
        }
    }

    fun setPhotoURL(url: String?){
        if (url != null) {
            photoURL.value = url
        }
    }

    fun citizenMessageToJson(citizenMessage: CitizenMessage): String {
        return Gson().toJson(citizenMessage)
    }

    fun jsonToCitizenMessage(jsonString: String): CitizenMessage {
        return Gson().fromJson(jsonString, CitizenMessage::class.java)
    }

    fun saveCitizenMessageToPrefs(context: Context, citizenMessage: CitizenMessage) {
        val jsonString = citizenMessageToJson(citizenMessage)
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("citizenMessage", jsonString).apply()
    }

    fun getCitizenMessageFromPrefs(context: Context): CitizenMessage? {
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jsonString = prefs.getString("citizenMessage", null)
        return if (jsonString != null) {
            jsonToCitizenMessage(jsonString)
        } else {
            null
        }
    }

    fun clearCitizenMessageFromPrefs(context: Context) {
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().remove("citizenMessage").apply()
    }


    val locationViewModel = LocationViewModel(context = context)
    suspend fun sentAlert() {
        val selectedLevel = selectedDangerLevelButton.value
        val selectedPhenomenon = selectedWeatherPhenomenon.value
        val description = alertDescription.value
        val photoURL = photoURL.value

        val locationData = locationViewModel.getLastLocation()

        // Get the current user ID from Firebase
        val userId = userId

        // Create a reference to the Firebase database
        val database = FirebaseDatabase.getInstance()

        // Create a reference to the "alertForms" node
        val myRef = database.getReference("alertForms")

        // Create a unique key for the new alert form
        val key = myRef.push().key

        // Create an instance of CitizenMessage
        val citizenMessage = CitizenMessage(
            userId = userId ?: "", // Use the user ID from Firebase
            message = description,
            criticalWeatherPhenomenon = selectedPhenomenon,
            location = locationData, // Replace with actual location data
            criticalLevel = selectedLevel,
            imageURL = photoURL
        )

        // Send the data to the server
        if (key != null) {
            myRef.child(key).setValue(citizenMessage)
        }
    }


}