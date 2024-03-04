package eu.tkacas.smartalert.models

import eu.tkacas.smartalert.R

enum class Months {
    SELECT_MONTH,
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December,
    ALL_MONTHS; // This is a special case for the dropdown component

    fun getStringId(): Int {
        return when (this) {
            SELECT_MONTH -> R.string.select_month
            January -> R.string.january
            February -> R.string.february
            March -> R.string.march
            April -> R.string.april
            May -> R.string.may
            June -> R.string.june
            July -> R.string.july
            August -> R.string.august
            September -> R.string.september
            October -> R.string.october
            November -> R.string.november
            December -> R.string.december
            ALL_MONTHS -> R.string.all_months
        }
    }
}

