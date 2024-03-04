package eu.tkacas.smartalert.models

data class PlacesResponse(
    val predictions: List<PlacePrediction>
)

data class PlacePrediction(
    val description: String,
    val place_id: String
)