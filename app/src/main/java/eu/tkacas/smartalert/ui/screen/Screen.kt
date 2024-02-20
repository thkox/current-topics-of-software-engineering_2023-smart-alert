package eu.tkacas.smartalert.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.stringResource
import eu.tkacas.smartalert.R

sealed class Screen<ImageVector>(val titleResId: Int, val route: String) {

    sealed class SettingsScreen(titleResId: Int, route: String, @DrawableRes  val icon: Int) : Screen<Any?>(titleResId, route) {

        object Account : SettingsScreen(
            R.string.account,
            "Account",
            R.drawable.account_box
        )

        object MyReportsHistory : SettingsScreen(
            R.string.reports_history,
            "MyReportsHistory",
            R.drawable.manage_history
        )

        object Language : SettingsScreen(
            R.string.language,
            "Language",
            R.drawable.language
        )

        object Analytics : SettingsScreen(
            R.string.analytics,
            "Analytics",
            R.drawable.analytics
        )

        object About : SettingsScreen(
            R.string.about,
            "About",
            R.drawable.about
        )

        object Logout : SettingsScreen(
            R.string.logout,
            "Logout",
            R.drawable.logout
        )
    }

    sealed class HomeCitizen(titleResId: Int, route: String, @DrawableRes val icon: Int) : Screen<Any?>(titleResId, route) {
        object AlertForm : HomeCitizen(
            R.string.alert_form,
            "AlertForm",
            R.drawable.alert
        )

        object Alert : HomeCitizen(
            R.string.my_reports,
            "MyReports",
            R.drawable.important
        )
    }

    sealed class HomeEmployee(titleResId: Int, route: String, @DrawableRes val icon: Int) : Screen<Any?>(titleResId, route) {
        object AlertCitizenForm : HomeEmployee(
            R.string.alert_form,
            "AlertForm",
            R.drawable.alert
        )

        object GroupEventsByLocation : HomeEmployee(
            R.string.events,
            "GroupEventsByLocation",
            R.drawable.event
        )

        object MapWithPinnedReports : HomeEmployee(
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
    Screen.HomeCitizen.Alert
)

val screensInHomeEmployee = listOf(
    Screen.HomeEmployee.AlertCitizenForm,
    Screen.HomeEmployee.GroupEventsByLocation,
    Screen.HomeEmployee.MapWithPinnedReports
)
