package eu.tkacas.smartalert.models

data class SingleLocationCriticalWeatherPhenomenonData(
    val location: String,
    val emLevel: Int,
    val message: String,
    val imageURL: String,
    val timeStamp: Long
)

data class ListOfSingleLocationCriticalWeatherPhenomenonData(
    val list: MutableList<SingleLocationCriticalWeatherPhenomenonData> = mutableListOf()
)