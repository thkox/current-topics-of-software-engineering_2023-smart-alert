package eu.tkacas.smartalert.ui.screen

import androidx.annotation.DrawableRes
import eu.tkacas.smartalert.R

sealed class Screen<ImageVector>(val title: String, val route: String) {

    sealed class SettingsScreen(title: String, route: String, @DrawableRes  val icon: Int) : Screen<Any?>(title, route) {

        object Account : SettingsScreen(
            "Account",
            "Account",
            R.drawable.account_box
        )

        object MyReportsHistory : SettingsScreen(
            "Reports History",
            "MyReportsHistory",
            R.drawable.manage_history
        )

        object Language : SettingsScreen(
            "Language",
            "Language",
            R.drawable.language
        )

        object Analytics : SettingsScreen(
            "Analytics",
            "Analytics",
            R.drawable.analytics
        )

        object About : SettingsScreen(
            "About",
            "About",
            R.drawable.about
        )
    }

    sealed class HomeCitizen(title: String, route: String, @DrawableRes val icon: Int) : Screen<Any?>(title, route) {
        object AlertForm : HomeCitizen(
            "AlertForm",
            "AlertForm",
            R.drawable.alert
        )

        object Alert : HomeCitizen(
            "My Reports",
            "MyReports",
            R.drawable.important
        )
    }

    sealed class HomeEmployee(title: String, route: String, @DrawableRes val icon: Int) : Screen<Any?>(title, route) {
        object AlertCitizenForm : HomeEmployee(
            "AlertForm",
            "AlertForm",
            R.drawable.alert
        )

        object GroupEventsByLocation : HomeEmployee(
            "Events",
            "GroupEventsByLocation",
            R.drawable.event
        )

        object MapWithPinnedReports : HomeEmployee(
            "Map",
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
    Screen.SettingsScreen.About
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
