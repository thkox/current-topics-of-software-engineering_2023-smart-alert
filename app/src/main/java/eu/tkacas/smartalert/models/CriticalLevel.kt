package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class CriticalLevel {
    LOW, NORMAL, HIGH;

    fun getStringId(): Int {
        return when (this) {
            LOW -> R.string.low
            NORMAL -> R.string.normal
            HIGH -> R.string.high
        }
    }

    fun getColor(): Int {
        return when (this) {
            LOW -> R.color.colorWhite
            NORMAL -> R.color.selective_yellow
            HIGH -> R.color.ut_orange
        }
    }
}

enum class CriticalLevelDropdown {
    AllALERTS, LOW, NORMAL, HIGH;

    fun getStringId(): Int {
        return when (this) {
            AllALERTS -> R.string.all_alerts
            LOW -> R.string.low
            NORMAL -> R.string.normal
            HIGH -> R.string.high
        }
    }
}