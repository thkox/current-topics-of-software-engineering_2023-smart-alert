package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class Months {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER,
    ALL_MONTHS; // This is a special case for the dropdown component

    fun getStringId(): Int {
        return when (this) {
            JANUARY -> R.string.january
            FEBRUARY -> R.string.february
            MARCH -> R.string.march
            APRIL -> R.string.april
            MAY -> R.string.may
            JUNE -> R.string.june
            JULY -> R.string.july
            AUGUST -> R.string.august
            SEPTEMBER -> R.string.september
            OCTOBER -> R.string.october
            NOVEMBER -> R.string.november
            DECEMBER -> R.string.december
            ALL_MONTHS -> R.string.all_months
        }
    }
}

