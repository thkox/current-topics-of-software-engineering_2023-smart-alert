package eu.tkacas.smartalert.database.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    companion object {
        private const val databaseVersion = 1
        private const val databaseName = "NotificationHistory"
        private const val table = "Notifications"
        private const val keyId = "id"
        private const val keyMessage = "message"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MESSAGES_TABLE = ("CREATE TABLE " + table + "("
                + keyId + " INTEGER PRIMARY KEY," + keyMessage + " TEXT" + ")")
        db.execSQL(CREATE_MESSAGES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $table")
        onCreate(db)
    }

    fun addMessage(message: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(keyMessage, message)
        db.insert(table, null, values)
        db.close()
    }
}