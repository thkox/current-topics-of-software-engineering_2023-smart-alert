package eu.tkacas.smartalert.models

data class LocationCriticalWeatherPhenomenonData(
    val location: String,
    val numOfReports: Int
)

data class ListOfLocationCriticalWeatherPhenomenonData(
    val list: MutableList<LocationCriticalWeatherPhenomenonData> = mutableListOf()
)