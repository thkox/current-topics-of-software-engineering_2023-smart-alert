package eu.tkacas.smartalert.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import eu.tkacas.smartalert.MainActivity
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.cloud.saveToken
import eu.tkacas.smartalert.models.Bounds
import eu.tkacas.smartalert.models.CriticalLevel
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.LocationData
import eu.tkacas.smartalert.viewmodel.LocationViewModel
import kotlinx.coroutines.runBlocking

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FirebaseMessagingService", "Refreshed token: $token")
        saveToken(token)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Get the additional data
        val data = message.data
        val locationBoundsJson = data["locationBounds"]
        val weatherPhenomenonJson = data["weatherPhenomenon"]
        val criticalLevelJson = data["criticalLevel"]
        val locationNameJson = data["locationName"]

        // Parse the locationBounds data into a Bounds object
        val locationBounds = Gson().fromJson(locationBoundsJson, Bounds::class.java)
        val weatherPhenomenon = Gson().fromJson(weatherPhenomenonJson, CriticalWeatherPhenomenon::class.java)
        val criticalLevel = Gson().fromJson(criticalLevelJson, CriticalLevel::class.java)
        val locationName = Gson().fromJson(locationNameJson, String::class.java)

        // Create a custom message body
//        val messageBody = "Location: $locationName\n" +
//                "Weather Phenomenon: ${this.getString(weatherPhenomenon.getStringId())}\n" +
//                "Critical Level: ${this.getString(criticalLevel.getStringId())}\n" +
//                "Location Bounds: $locationBounds"

        val messageBody = "URGENT: Residents in the affected area of $locationName," + " please take immediate precautions.\n" +
            "This ${this.getString(weatherPhenomenon.getStringId())} phenomenon is with ${this.getString(criticalLevel.getStringId())} severity.\n" +
            "Stay indoors, avoid travel and follow local authorities' instructions.\n" +
            "Your safety is our top priority.\n" + "Stay tuned for updates."


        // Get the user's current location
        val locationViewModel = LocationViewModel(this)
        val userLocation = runBlocking { locationViewModel.getLastLocation() }

        // Check if the user's location is within the locationBounds
        if (userLocation != null && isUserInBounds(userLocation, locationBounds)) {
            // If the user is in the geolocation block, show the notification
            sendNotification(messageBody, weatherPhenomenon)
        }
    }

    private fun isUserInBounds(userLocation: LocationData, bounds: Bounds): Boolean {
        return userLocation.latitude in bounds.southwest?.lat!!..bounds.northeast?.lat!! &&
                userLocation.longitude in bounds.southwest?.lng!!..bounds.northeast?.lng!!
    }

    private fun sendNotification(messageBody: String, weatherPhenomenon: CriticalWeatherPhenomenon) {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }


        val builder = NotificationCompat.Builder(this, "locationServiceChannel")
            .setContentTitle("Smart Alert")
            .setContentText(this.getString(weatherPhenomenon.getStringId()))
            .setSmallIcon(R.drawable.smart_alert_logo_full_transparent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            builder.setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        }

        val notification = builder.build()
        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun createNotificationChannel() {
        val locationServiceChannel = NotificationChannel(
            "locationServiceChannel",
            "Location Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(locationServiceChannel)
    }


}