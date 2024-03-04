package eu.tkacas.smartalert

import android.app.Application
import com.google.firebase.FirebaseApp

class SmartAlertApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}