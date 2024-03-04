package eu.tkacas.smartalert.models

data class LatLng(val lat: Double?, val lng: Double?)

data class Bounds(val northeast: LatLng?, val southwest: LatLng?)