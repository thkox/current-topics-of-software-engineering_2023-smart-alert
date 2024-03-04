package eu.tkacas.smartalert.models

data class SingleLocationCriticalWeatherPhenomenonData(
    val alertID: String,
    val location: String,
    val emLevel: CriticalLevel,
    val message: String,
    val imageURL: String,
    val timeStamp: String
)

data class ListOfSingleLocationCriticalWeatherPhenomenonData(
    val list: MutableList<SingleLocationCriticalWeatherPhenomenonData> = mutableListOf()
)