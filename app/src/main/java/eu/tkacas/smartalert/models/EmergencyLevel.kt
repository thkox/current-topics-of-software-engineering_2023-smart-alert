package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class EmergencyLevel {
    LOW, NORMAL, HIGH;

    fun getStringId(): Int {
        return when (this) {
            EmergencyLevel.LOW -> R.string.low
            EmergencyLevel.NORMAL -> R.string.normal
            EmergencyLevel.HIGH -> R.string.high
        }
    }
}