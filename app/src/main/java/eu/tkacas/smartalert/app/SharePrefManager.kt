package eu.tkacas.smartalert.app

import android.content.Context
import android.content.SharedPreferences
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon

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

    fun setCriticalWeatherPhenomenon(name: String) {
        sharedPreferences.edit().putString("critical_weather_phenomenon", name).apply()
    }

    fun getCriticalWeatherPhenomenon(): CriticalWeatherPhenomenon {
        val name = sharedPreferences.getString("critical_weather_phenomenon", "WILDFIRE")
        return CriticalWeatherPhenomenon.valueOf(name!!)
    }

    fun setLocationID(address: String) {
        sharedPreferences.edit().putString("location_id", address).apply()
    }

    fun getLocationID(): String {
        val address = sharedPreferences.getString("location_id", "Kifissia")
        return address!!
    }

    // NOT USED YET
    fun removeLocationID() {
        sharedPreferences.edit().remove("location_id").apply()
    }

    fun setPreviousScreen(screen: String) {
        sharedPreferences.edit().putString("previous_screen", screen).apply()
    }

    fun getPreviousScreen(): String {
        val screen = sharedPreferences.getString("previous_screen", "Home")
        return screen!!
    }

    fun setBoundsNorthEastLat(lat: Double?) {
        sharedPreferences.edit().putString("bounds_north_east_lat", lat.toString()).apply()
    }

    fun setBoundsNorthEastLng(lng: Double?) {
        sharedPreferences.edit().putString("bounds_north_east_lng", lng.toString()).apply()
    }

    fun setBoundsSouthWestLat(lat: Double?) {
        sharedPreferences.edit().putString("bounds_south_west_lat", lat.toString()).apply()
    }

    fun setBoundsSouthWestLng(lng: Double?) {
        sharedPreferences.edit().putString("bounds_south_west_lng", lng.toString()).apply()
    }

    fun getBoundsNorthEastLat(): Double {
        val lat = sharedPreferences.getString("bounds_north_east_lat", "38.0000")
        return lat!!.toDouble()
    }

    fun getBoundsNorthEastLng(): Double {
        val lng = sharedPreferences.getString("bounds_north_east_lng", "23.0000")
        return lng!!.toDouble()
    }

    fun getBoundsSouthWestLat(): Double {
        val lat = sharedPreferences.getString("bounds_south_west_lat", "37.0000")
        return lat!!.toDouble()
    }

    fun getBoundsSouthWestLng(): Double {
        val lng = sharedPreferences.getString("bounds_south_west_lng", "22.0000")
        return lng!!.toDouble()
    }

}