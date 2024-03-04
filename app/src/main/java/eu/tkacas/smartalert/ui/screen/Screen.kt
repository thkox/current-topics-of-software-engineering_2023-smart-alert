package eu.tkacas.smartalert.ui.screen

import androidx.annotation.DrawableRes
import eu.tkacas.smartalert.R

sealed class Screen<ImageVector>(val titleResId: Int, val route: String) {
    sealed class SettingsScreen(titleResId: Int, route: String, @DrawableRes val icon: Int) :
        Screen<Any?>(titleResId, route) {

        data object Account : SettingsScreen(
            R.string.account,
            "Account",
            R.drawable.account_box
        )

        data object MyReportsHistory : SettingsScreen(
            R.string.reports_history,
            "MyReportsHistory",
            R.drawable.manage_history
        )

        data object Language : SettingsScreen(
            R.string.language,
            "Language",
            R.drawable.language
        )

        data object Analytics : SettingsScreen(
            R.string.analytics,
            "Analytics",
            R.drawable.analytics
        )

        data object About : SettingsScreen(
            R.string.about,
            "About",
            R.drawable.about
        )

        data object Logout : SettingsScreen(
            R.string.logout,
            "Logout",
            R.drawable.logout
        )
    }

    sealed class HomeCitizen(titleResId: Int, route: String, @DrawableRes val icon: Int) :
        Screen<Any?>(titleResId, route) {
        data object AlertForm : HomeCitizen(
            R.string.alert_form,
            "AlertForm",
            R.drawable.alert
        )
    }

    sealed class HomeEmployee(titleResId: Int, route: String, @DrawableRes val icon: Int) :
        Screen<Any?>(titleResId, route) {
        data object AlertCitizenForm : HomeEmployee(
            R.string.alert_form,
            "AlertForm",
            R.drawable.alert
        )

        data object GroupEventsByLocation : HomeEmployee(
            R.string.events,
            "GroupEventsByLocation",
            R.drawable.event
        )

        data object EventsByLocation : HomeEmployee(
            R.string.events,
            "EventsByLocation",
            R.drawable.event
        )

        data object MapWithPinnedReports : HomeEmployee(
            R.string.map,
            "Map",
            R.drawable.map
        )
    }
}

val screensInSettings = listOf(
    Screen.SettingsScreen.Account,
    Screen.SettingsScreen.MyReportsHistory,
    Screen.SettingsScreen.Language,
    Screen.SettingsScreen.Analytics,
    Screen.SettingsScreen.About,
    Screen.SettingsScreen.Logout
)

val screensInHomeCitizen = listOf(
    Screen.HomeCitizen.AlertForm,
)

val screensInHomeEmployee = listOf(
    Screen.HomeEmployee.AlertCitizenForm,
    Screen.HomeEmployee.GroupEventsByLocation,
    Screen.HomeEmployee.EventsByLocation,
    Screen.HomeEmployee.MapWithPinnedReports
)
