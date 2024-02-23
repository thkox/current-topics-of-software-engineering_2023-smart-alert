package eu.tkacas.smartalert

import com.google.firebase.FirebaseApp
import android.app.Application

class SmartAlertApp : Application() {
    override fun onCreate() {
        super.onCreate()


        FirebaseApp.initializeApp(this)
    }
}