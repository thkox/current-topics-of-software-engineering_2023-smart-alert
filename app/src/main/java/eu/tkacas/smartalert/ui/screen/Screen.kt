package eu.tkacas.smartalert.ui.screen

import androidx.annotation.DrawableRes
import eu.tkacas.smartalert.R

sealed class Screen(val title: String, val route: String) {
    sealed class  SettingsScreen(val sTitle: String, val sRoute: String, @DrawableRes val icon: Int) : Screen(sTitle, sRoute) {
        object Account: SettingsScreen(
            "Account",
            "Account",
            R.drawable.account_box
        )

        object MyReportsHistory: SettingsScreen(
            "Reports History",
            "MyReportsHistory",
            R.drawable.manage_history
        )

        object Language: SettingsScreen(
            "Language",
            "Language",
            R.drawable.language
        )

        object Analytics: SettingsScreen(
            "Analytics",
            "Analytics",
            R.drawable.analytics

        )

        object About: SettingsScreen(
            "About",
            "About",
            R.drawable.about
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