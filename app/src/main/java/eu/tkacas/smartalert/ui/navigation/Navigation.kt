package eu.tkacas.smartalert.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.ui.screen.citizen.HomeCitizenScreen
import eu.tkacas.smartalert.ui.screen.settings.*

@Composable
fun Navigation(navController: NavController = rememberNavController()) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "home",
    ) {
        composable("home") {
            HomeCitizenScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }

        composable("account") {
            AccountScreen()
        }
        composable("myReportsHistory") {
            MyReportsHistoryScreen()
        }
        composable("language") {
            LanguageScreen()
        }
        composable("analytics") {
            AnalyticsScreen()
        }
        composable("about") {
            AboutScreen()
        }
    }
}