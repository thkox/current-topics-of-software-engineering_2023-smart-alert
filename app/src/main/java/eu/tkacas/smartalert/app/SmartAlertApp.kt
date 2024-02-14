package eu.tkacas.smartalert.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import eu.tkacas.smartalert.navigation.Screen
import eu.tkacas.smartalert.navigation.SmartAlertAppRouter
import eu.tkacas.smartalert.screens.SignUpScreen
import eu.tkacas.smartalert.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tkacas.smartalert.screens.HomeScreen
import eu.tkacas.smartalert.screens.LoginScreen
import eu.tkacas.smartalert.screens.PermissionsScreen
import eu.tkacas.smartalert.screens.TermsAndConditionsScreen
import eu.tkacas.smartalert.screens.WelcomeScreen

@Composable
fun SmartAlertApp(homeViewModel: HomeViewModel = viewModel()) {

    homeViewModel.checkForActiveSession()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        if (homeViewModel.isUserLoggedIn.value == true) {
            SmartAlertAppRouter.navigateTo(Screen.WelcomeScreen)
        }

        Crossfade(targetState = SmartAlertAppRouter.currentScreen, label = "") { currentState ->
            when (currentState.value) {
                is Screen.WelcomeScreen -> {
                    WelcomeScreen()
                }

                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen()
                }

                is Screen.HomeScreen -> {
                    PermissionsScreen()
                }
            }
        }

    }
}
