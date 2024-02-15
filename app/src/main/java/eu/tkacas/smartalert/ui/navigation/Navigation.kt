package eu.tkacas.smartalert.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.ui.screen.Screen
import eu.tkacas.smartalert.ui.screen.citizen.HomeCitizenScreen
import eu.tkacas.smartalert.ui.screen.settings.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import eu.tkacas.smartalert.ui.screen.auth.LoginScreen
import eu.tkacas.smartalert.ui.screen.auth.SignUpScreen
import eu.tkacas.smartalert.ui.screen.auth.TermsAndConditionsScreen
import eu.tkacas.smartalert.ui.screen.citizen.AlertFormScreen
import eu.tkacas.smartalert.ui.screen.citizen.AlertScreen
import eu.tkacas.smartalert.ui.screen.employee.AlertCitizensFormScreen
import eu.tkacas.smartalert.ui.screen.employee.GroupEventsByLocationScreen
import eu.tkacas.smartalert.ui.screen.employee.HomeEmployeeScreen
import eu.tkacas.smartalert.ui.screen.employee.MapWithPinnedReportsScreen
import eu.tkacas.smartalert.ui.screen.intro.PermissionsScreen
import eu.tkacas.smartalert.ui.screen.intro.WelcomeScreen
import eu.tkacas.smartalert.ui.screen.screensInHomeCitizen
import eu.tkacas.smartalert.ui.screen.screensInHomeEmployee
import eu.tkacas.smartalert.ui.screen.screensInSettings

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavController = rememberNavController()) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Update the title and icon based on the current route
    val currentScreen = when (currentRoute) {
        "homeCitizen" -> screensInHomeCitizen.find { it.route == currentRoute }
        "homeEmployee" -> screensInHomeEmployee.find { it.route == currentRoute }
        else -> screensInSettings.find { it.route == currentRoute }
    }

    // Exclude screens that should not have a TopAppBar
    val excludedRoutes = listOf("welcome", "permissions", "login", "signUp")

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.email?.contains("@civilprotection.gr") == true){
        "homeEmployee"
    } else if (FirebaseAuth.getInstance().currentUser != null ) {
        "homeCitizen"
    } else {
        "welcome"
    }

    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination
    ) {
        // TODO: Needs to be changed to "homeEmployee" when the employee screen is ready
        composable("welcome") { WelcomeScreen(navController) }
        composable("permissions") { PermissionsScreen() }
        composable("login") { LoginScreen(navController) }
        composable("signUp") { SignUpScreen(navController) }
        composable("termsAndConditions") { TermsAndConditionsScreen() }
        composable("homeCitizen") { HomeCitizenScreen(navController) }
        composable("homeEmployee") { HomeEmployeeScreen() }
        composable("settings") { SettingsScreen(navController) }
        composable("alertForm") { AlertFormScreen() }
        composable("alert") { AlertScreen() }
        composable("alertCitizensForm") { AlertCitizensFormScreen() }
        composable("groupEventsByLocation") { GroupEventsByLocationScreen() }
        composable("mapWithPinnedReports") { MapWithPinnedReportsScreen() }
        screensInSettings.forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    is Screen.SettingsScreen.Account -> AccountScreen()
                    is Screen.SettingsScreen.MyReportsHistory -> MyReportsHistoryScreen()
                    is Screen.SettingsScreen.Language -> LanguageScreen()
                    is Screen.SettingsScreen.Analytics -> AnalyticsScreen()
                    is Screen.SettingsScreen.About -> AboutScreen()
                    else -> {}
                }
            }
        }
    }
}