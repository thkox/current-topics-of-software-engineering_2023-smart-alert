package eu.tkacas.smartalert.database.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import eu.tkacas.smartalert.models.CriticalLevel
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.HistoryMessage
import eu.tkacas.smartalert.models.ListOfHistoryMessages

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    companion object {
        private const val databaseVersion = 1
        private const val databaseName = "NotificationHistory"
        private const val table = "Notifications"
        private const val keyId = "id"
        private const val keyMessage = "message"
        private const val keyWeatherPhenomenon = "weatherPhenomenon"
        private const val keyCriticalLevel = "criticalLevel"
        private const val keyLocationName = "locationName"
        private const val keyCurrentTime = "currentTime"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MESSAGES_TABLE = ("CREATE TABLE " + table + "("
                + keyId + " INTEGER PRIMARY KEY,"
                + keyMessage + " TEXT,"
                + keyWeatherPhenomenon + " TEXT,"
                + keyCriticalLevel + " TEXT,"
                + keyLocationName + " TEXT,"
                + keyCurrentTime + " TEXT" + ")")
        db.execSQL(CREATE_MESSAGES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $table")
        onCreate(db)
    }

    fun addMessage(
        message: String,
        weatherPhenomenon: String,
        criticalLevel: String,
        locationName: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(keyMessage, message)
        values.put(keyWeatherPhenomenon, weatherPhenomenon)
        values.put(keyCriticalLevel, criticalLevel)
        values.put(keyLocationName, locationName)

        // Get the current time in UTC+2
        val zoneId = ZoneId.of("UTC+2")
        val currentTime = ZonedDateTime.now(zoneId)

        // Format the ZonedDateTime object to a string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formattedCurrentTime = currentTime.format(formatter)

        // Insert the formatted string into the keyCurrentTime column
        values.put(keyCurrentTime, formattedCurrentTime)

        db.insert(table, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getMessages(onComplete: (Boolean, ListOfHistoryMessages?, String?) -> Unit) {
        val messagesList = mutableStateOf<ListOfHistoryMessages?>(ListOfHistoryMessages(mutableStateListOf()))
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $table", null)

        if (cursor.moveToFirst()) {
            do {
                val message = cursor.getString(cursor.getColumnIndex(keyMessage))
                val weatherPhenomenonString = cursor.getString(cursor.getColumnIndex(keyWeatherPhenomenon))
                val weatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenonString)
                val criticalLevelString = cursor.getString(cursor.getColumnIndex(keyCriticalLevel))
                val criticalLevel = CriticalLevel.valueOf(criticalLevelString)
                val locationName = cursor.getString(cursor.getColumnIndex(keyLocationName))
                val messageTime = cursor.getString(cursor.getColumnIndex(keyCurrentTime))
                messagesList.value?.list?.add(HistoryMessage(message, weatherPhenomenon, criticalLevel, locationName, messageTime))
            } while (cursor.moveToNext())
            onComplete(true, messagesList.value, null)
        } else {
            onComplete(false, null, "No messages found")
        }
    }
}