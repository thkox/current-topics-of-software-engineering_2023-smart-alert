package eu.tkacas.smartalert.app

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun setIsEmployee(isEmployee: Boolean) {
        sharedPreferences.edit().putBoolean("is_employee", isEmployee).apply()
    }

    fun isEmployee(): Boolean {
        return sharedPreferences.getBoolean("is_employee", false)
    }

    fun removeIsEmployee() {
        sharedPreferences.edit().remove("is_employee").apply()
    }
}