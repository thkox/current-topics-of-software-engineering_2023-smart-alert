package eu.tkacas.smartalert.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.ui.screen.Screen
import eu.tkacas.smartalert.ui.screen.citizen.HomeCitizenScreen
import eu.tkacas.smartalert.ui.screen.settings.*
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.cloud.userExists
import eu.tkacas.smartalert.ui.screen.auth.ForgotPasswordScreen
import eu.tkacas.smartalert.cloud.signOutUser
import eu.tkacas.smartalert.cloud.userIsEmployee
import eu.tkacas.smartalert.ui.screen.auth.LoginScreen
import eu.tkacas.smartalert.ui.screen.auth.SignUpScreen
import eu.tkacas.smartalert.ui.screen.auth.TermsAndConditionsScreen
import eu.tkacas.smartalert.ui.screen.citizen.AlertFormScreen
import eu.tkacas.smartalert.ui.screen.citizen.AlertScreen
import eu.tkacas.smartalert.ui.screen.citizen.Camera.CameraScreen
import eu.tkacas.smartalert.ui.screen.employee.AlertCitizensFormScreen
import eu.tkacas.smartalert.ui.screen.employee.GroupEventsByLocationScreen
import eu.tkacas.smartalert.ui.screen.employee.HomeEmployeeScreen
import eu.tkacas.smartalert.ui.screen.employee.MapWithPinnedReportsScreen
import eu.tkacas.smartalert.ui.screen.intro.PermissionsScreen
import eu.tkacas.smartalert.ui.screen.intro.WelcomeScreen
import eu.tkacas.smartalert.ui.screen.screensInHomeCitizen
import eu.tkacas.smartalert.ui.screen.screensInHomeEmployee
import eu.tkacas.smartalert.ui.screen.screensInSettings

@Composable
fun Navigation(navController: NavController = rememberNavController()) {


    val startDestination =
        if (userExists()) {
            "home"
        } else {
            "welcome"
        }

    val context = LocalContext.current

    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination
    ) {
        // TODO: Needs to be changed to "homeEmployee" when the employee screen is ready
        composable("welcome") { WelcomeScreen(navController) }
        composable("permissions") {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                navController.navigate("home")

            }
            else {
                PermissionsScreen(navController)
            }

        } // TODO: Add conditions whether to show this screen or not based on the app give permissions.
        composable("login") { LoginScreen(navController) }
        composable("signUp") { SignUpScreen(navController) }
        composable("termsAndConditions") { TermsAndConditionsScreen() }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }
        composable("alertCitizensForm") { AlertCitizensFormScreen(navController) }
        composable("alertForm") { AlertFormScreen(navController) }
        composable("camera") { CameraScreen(navController) }
        composable("home") {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (userExists() && userIsEmployee()) {
                    HomeEmployeeScreen(navController)
                } else if (userExists()) {
                    HomeCitizenScreen(navController)
                } else {
                    // Redirect to login screen if user is not authenticated
                    navController.navigate("welcome")
                }
            } else {
                navController.navigate("permissions")
            }

        }
        composable("settings") { SettingsScreen(navController) }

        screensInHomeCitizen.forEach { screen ->
            composable(screen.route) {
                if (userExists()) {
                    when (screen) {
                        is Screen.HomeCitizen.AlertForm -> AlertFormScreen(navController)
                        is Screen.HomeCitizen.Alert -> AlertScreen(navController)
                    }
                } else {
                    // Redirect to login screen if user is not authenticated
                    navController.navigate("welcome")
                }
            }
        }

        screensInHomeEmployee.forEach { screen ->
            composable(screen.route) {
                if (userExists() && userIsEmployee()) {
                    when (screen) {
                        is Screen.HomeEmployee.AlertCitizenForm -> AlertCitizensFormScreen(navController)
                        is Screen.HomeEmployee.GroupEventsByLocation -> GroupEventsByLocationScreen(navController)
                        is Screen.HomeEmployee.MapWithPinnedReports -> MapWithPinnedReportsScreen(navController)
                    }
                } else {
                    // Redirect to login screen if user is not authenticated
                    navController.navigate("welcome")
                }
            }
        }
        screensInSettings.forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    is Screen.SettingsScreen.Account -> AccountScreen(navController)
                    is Screen.SettingsScreen.MyReportsHistory -> MyReportsHistoryScreen(navController)
                    is Screen.SettingsScreen.Language -> LanguageScreen(navController)
                    is Screen.SettingsScreen.Analytics -> AnalyticsScreen(navController)
                    is Screen.SettingsScreen.About -> AboutScreen(navController)
                    is Screen.SettingsScreen.Logout -> {
                        signOutUser()
                        navController.navigate("welcome")
                    }
                }
            }
        }
    }
}