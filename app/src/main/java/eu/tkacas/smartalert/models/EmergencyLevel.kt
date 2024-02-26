package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class EmergencyLevel {
    LOW, NORMAL, HIGH;

    // TODO: Add a function to get the string id of the emergency level
    fun getStringId(): Int {
        return when (this) {
            LOW -> R.string.low
            NORMAL -> R.string.normal
            HIGH -> R.string.high
        }
    }
}