package eu.tkacas.smartalert.models

data class Alert(
    val area: String? = null,
    val criticalWeatherPhenomenon: CriticalWeatherPhenomenon,
    val criticalLevel: Int,
    val timestamp: Long = System.currentTimeMillis()
)
