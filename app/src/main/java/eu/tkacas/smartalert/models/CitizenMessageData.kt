package eu.tkacas.smartalert.models

data class CitizenMessage(
    val userId: Int,
    val message: String,
    val criticalWeatherPhenomenon: CriticalWeatherPhenomenon,
    val location: LocationData,
    val timestamp: Long,
    val photoThumb: String? = null,
)

data class ArchivedMessages(
    val messages: List<CitizenMessage>
)

