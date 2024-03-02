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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import eu.tkacas.smartalert.MainActivity
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.cloud.saveToken

class NotificationService : FirebaseMessagingService() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        // TODO edit the message to get the info that we want

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val check = checkCurrentLocation()
        if(check){
            // TODO replace message, with the custom message


        }
        message.notification?.body?.let { sendNotification(it) }
    }

    private fun checkCurrentLocation(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    Log.d("NotificationService", "Location: ${location.latitude}, ${location.longitude}")
                    // TODO check the location if it is in the circle

                }
            }
        return false
    }

    private fun sendNotification(messageBody: String) {
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
            .setContentText(messageBody)
            .setSmallIcon(R.drawable.smart_alert_logo_full_transparent)
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