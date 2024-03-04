package eu.tkacas.smartalert.app

import android.content.Context
import android.content.SharedPreferences
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon

class SharedPrefManager(context: Context) {
    private val _sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun setIsEmployee(isEmployee: Boolean) {
        _sharedPreferences.edit().putBoolean("is_employee", isEmployee).apply()
    }

    fun isEmployee(): Boolean {
        return _sharedPreferences.getBoolean("is_employee", false)
    }

    fun removeIsEmployee() {
        _sharedPreferences.edit().remove("is_employee").apply()
    }

    fun setCriticalWeatherPhenomenon(name: String) {
        _sharedPreferences.edit().putString("critical_weather_phenomenon", name).apply()
    }

    fun getCriticalWeatherPhenomenon(): CriticalWeatherPhenomenon {
        val name = _sharedPreferences.getString("critical_weather_phenomenon", "EARTHQUAKE")
        return CriticalWeatherPhenomenon.valueOf(name!!)
    }

    fun setLocationID(address: String) {
        _sharedPreferences.edit().putString("location_id", address).apply()
    }

    fun getLocationID(): String {
        val address = _sharedPreferences.getString("location_id", "Kifissia")
        return address!!
    }

    fun setLocationName(name: String?) {
        _sharedPreferences.edit().putString("location_name", name).apply()
    }

    fun getLocationName(): String {
        val name = _sharedPreferences.getString("location_name", "Kifissia")
        return name!!
    }

    fun setPreviousScreen(screen: String) {
        _sharedPreferences.edit().putString("previous_screen", screen).apply()
    }

    fun getPreviousScreen(): String {
        val screen = _sharedPreferences.getString("previous_screen", "Home")
        return screen!!
    }

    fun setBoundsNorthEastLat(lat: Double?) {
        _sharedPreferences.edit().putString("bounds_north_east_lat", lat.toString()).apply()
    }

    fun setBoundsNorthEastLng(lng: Double?) {
        _sharedPreferences.edit().putString("bounds_north_east_lng", lng.toString()).apply()
    }

    fun setBoundsSouthWestLat(lat: Double?) {
        _sharedPreferences.edit().putString("bounds_south_west_lat", lat.toString()).apply()
    }

    fun setBoundsSouthWestLng(lng: Double?) {
        _sharedPreferences.edit().putString("bounds_south_west_lng", lng.toString()).apply()
    }

    fun getBoundsNorthEastLat(): Double {
        val lat = _sharedPreferences.getString("bounds_north_east_lat", "0.0")
        return lat!!.toDouble()
    }

    fun getBoundsNorthEastLng(): Double {
        val lng = _sharedPreferences.getString("bounds_north_east_lng", "0.0")
        return lng!!.toDouble()
    }

    fun getBoundsSouthWestLat(): Double {
        val lat = _sharedPreferences.getString("bounds_south_west_lat", "0.0")
        return lat!!.toDouble()
    }

    fun getBoundsSouthWestLng(): Double {
        val lng = _sharedPreferences.getString("bounds_south_west_lng", "0.0")
        return lng!!.toDouble()
    }

}