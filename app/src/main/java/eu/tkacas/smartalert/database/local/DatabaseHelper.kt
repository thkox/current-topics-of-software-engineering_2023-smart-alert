package eu.tkacas.smartalert.database.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import eu.tkacas.smartalert.models.CriticalLevel
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.HistoryMessage
import eu.tkacas.smartalert.models.ListOfHistoryMessages
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotificationHistory"
        private const val TABLE = "Notifications"
        private const val KEY_ID = "id"
        private const val KEY_MESSAGE = "message"
        private const val KEY_WEATHER_PHENOMENON = "weatherPhenomenon"
        private const val KEY_CRITICAL_LEVEL = "criticalLevel"
        private const val KEY_LOCATION_NAME = "locationName"
        private const val KEY_CURRENT_TIME = "currentTime"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createMessagesTable = ("CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_WEATHER_PHENOMENON + " TEXT,"
                + KEY_CRITICAL_LEVEL + " TEXT,"
                + KEY_LOCATION_NAME + " TEXT,"
                + KEY_CURRENT_TIME + " TEXT" + ")")
        db.execSQL(createMessagesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
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
        values.put(KEY_MESSAGE, message)
        values.put(KEY_WEATHER_PHENOMENON, weatherPhenomenon)
        values.put(KEY_CRITICAL_LEVEL, criticalLevel)
        values.put(KEY_LOCATION_NAME, locationName)

        // Get the current time in UTC+2
        val zoneId = ZoneId.of("UTC+2")
        val currentTime = ZonedDateTime.now(zoneId)

        // Format the ZonedDateTime object to a string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formattedCurrentTime = currentTime.format(formatter)

        // Insert the formatted string into the keyCurrentTime column
        values.put(KEY_CURRENT_TIME, formattedCurrentTime)

        db.insert(TABLE, null, values)
        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun getMessages(onComplete: (Boolean, ListOfHistoryMessages?, String?) -> Unit) {
        val messagesList =
            mutableStateOf<ListOfHistoryMessages?>(ListOfHistoryMessages(mutableStateListOf()))
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE ORDER BY $KEY_CURRENT_TIME DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val message = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE))
                val weatherPhenomenonString =
                    cursor.getString(cursor.getColumnIndex(KEY_WEATHER_PHENOMENON))
                val weatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenonString)
                val criticalLevelString = cursor.getString(cursor.getColumnIndex(KEY_CRITICAL_LEVEL))
                val criticalLevel = CriticalLevel.valueOf(criticalLevelString)
                val locationName = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_NAME))
                val messageTime = cursor.getString(cursor.getColumnIndex(KEY_CURRENT_TIME))
                messagesList.value?.list?.add(
                    HistoryMessage(
                        message,
                        weatherPhenomenon,
                        criticalLevel,
                        locationName,
                        messageTime
                    )
                )
            } while (cursor.moveToNext())
            onComplete(true, messagesList.value, null)
        } else {
            onComplete(false, null, "No messages found")
        }
    }
}