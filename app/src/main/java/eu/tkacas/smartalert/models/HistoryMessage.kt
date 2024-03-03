package eu.tkacas.smartalert.models

data class HistoryMessage(
    val message: String,
    val weatherPhenomenon: CriticalWeatherPhenomenon,
    val criticalLevel: CriticalLevel,
    val locationName: String,
    val messageTime: String
)

data class ListOfHistoryMessages(
    val list: MutableList<HistoryMessage> = mutableListOf()
)