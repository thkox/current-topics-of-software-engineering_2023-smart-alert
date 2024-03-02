package eu.tkacas.smartalert.models

data class PlaceDetailsResponse(
    val result: PlaceDetails
)

data class PlaceDetails(
    val geometry: Geometry
)

data class Geometry(
    val location: Location,
    val viewport: Viewport
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Viewport(
    val northeast: Location,
    val southwest: Location
)