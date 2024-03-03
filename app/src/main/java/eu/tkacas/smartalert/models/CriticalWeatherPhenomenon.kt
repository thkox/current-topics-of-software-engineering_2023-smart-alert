package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class CriticalWeatherPhenomenon {
    EARTHQUAKE, FLOOD, WILDFIRE, HEATWAVE, SNOWSTORM, STORM;

    fun getStringId(): Int {
        return when (this) {
            EARTHQUAKE -> R.string.earthquake
            FLOOD -> R.string.flood
            WILDFIRE -> R.string.wildfire
            HEATWAVE -> R.string.heatwave
            SNOWSTORM -> R.string.snowstorm
            STORM -> R.string.storm
        }
    }

    fun getImage(): Int {
        return when (this) {
            EARTHQUAKE -> R.drawable.earthquake
            FLOOD -> R.drawable.flood
            WILDFIRE -> R.drawable.wildfire
            HEATWAVE -> R.drawable.heatwave
            SNOWSTORM -> R.drawable.snowstorm
            STORM -> R.drawable.storm
        }
    }
}