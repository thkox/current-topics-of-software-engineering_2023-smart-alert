package eu.tkacas.smartalert.viewmodel.employee

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import kotlinx.coroutines.flow.MutableStateFlow

class HomeEmployeeViewModel(context : Context): ViewModel() {

    val firebase = FirebaseUtils()
    val sharedPrefManager = SharedPrefManager(context)

    val firstNameVal = MutableStateFlow(sharedPrefManager.getFirstName())



    init {
        fetchFirstName()
    }


    private fun fetchFirstName() {
        firebase.getFirstName { success, firstName, error ->
            if (success) {
                sharedPrefManager.setFirstName(firstName ?: "")
            } else {
                println("Error occurred: $error")
            }
        }
    }
}