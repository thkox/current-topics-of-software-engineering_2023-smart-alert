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
import eu.tkacas.smartalert.ui.screen.auth.ForgotPasswordScreen
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

@Composable
fun Navigation(navController: NavController = rememberNavController()) {

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
        composable("forgotPassword") { ForgotPasswordScreen(navController) }

        composable("homeCitizen") {
            if (FirebaseAuth.getInstance().currentUser != null) {
                HomeCitizenScreen(navController)
            } else {
                // Redirect to login screen if user is not authenticated
                navController.navigate("welcome")
            }
        }
        composable("homeEmployee") {
            if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.email?.contains("@civilprotection.gr") == true) {
                HomeEmployeeScreen(navController)
            } else {
                // Redirect to login screen if user is not authenticated
                navController.navigate("welcome")
            }
        }

        composable("settings") { SettingsScreen(navController) }

        screensInHomeCitizen.forEach { screen ->
            composable(screen.route) {
                if (FirebaseAuth.getInstance().currentUser != null) {
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
                if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.email?.contains("@civilprotection.gr") == true) {
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
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("welcome")
                    }
                }
            }
        }
    }
}