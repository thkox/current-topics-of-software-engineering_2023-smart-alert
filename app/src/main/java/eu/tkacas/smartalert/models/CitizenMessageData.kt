package eu.tkacas.smartalert.models

data class CitizenMessage(
    val message: String? = null,
    val criticalWeatherPhenomenon: CriticalWeatherPhenomenon,
    val criticalLevel: Int,
    val location: LocationData? = null,
    val timestamp: Long = System.currentTimeMillis(),
    var imageURL: String? = null
)

