package eu.tkacas.smartalert.models

data class Alert(
    val locationName: String,
    val locationBounds: Bounds,
    val criticalWeatherPhenomenon: CriticalWeatherPhenomenon,
    val criticalLevel: EmergencyLevel = EmergencyLevel.LOW,
    val timestamp: Long = System.currentTimeMillis()
)
