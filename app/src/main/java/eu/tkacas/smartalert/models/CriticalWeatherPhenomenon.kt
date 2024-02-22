package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class CriticalWeatherPhenomenon {
    EARTHQUAKE, FLOOD, WILDFIRE, RIVER_FLOOD, HEATWAVE, SNOWSTORM, STORM;

    fun getStringId(): Int {
        return when (this) {
            CriticalWeatherPhenomenon.EARTHQUAKE -> R.string.earthquake
            CriticalWeatherPhenomenon.FLOOD -> R.string.flood
            CriticalWeatherPhenomenon.WILDFIRE -> R.string.wildfire
            CriticalWeatherPhenomenon.RIVER_FLOOD -> R.string.river_flood
            CriticalWeatherPhenomenon.HEATWAVE -> R.string.heatwave
            CriticalWeatherPhenomenon.SNOWSTORM -> R.string.snowstorm
            CriticalWeatherPhenomenon.STORM -> R.string.storm
        }
    }
}