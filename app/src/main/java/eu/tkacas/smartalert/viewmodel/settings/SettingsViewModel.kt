package eu.tkacas.smartalert.viewmodel.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.app.SharedPrefManager

class SettingsViewModel(context: Context) : ViewModel() {
    val sharedPrefManager: SharedPrefManager = SharedPrefManager(context)
}