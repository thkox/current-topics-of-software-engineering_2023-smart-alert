package eu.tkacas.smartalert.models

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class LocationDataList(
    val locations: List<LocationData>
)

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

data class GeocodingResult(
    val formatted_addres: String
)

