package eu.tkacas.smartalert.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.tkacas.smartalert.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tkacas.smartalert.ui.screen.citizen.HomeScreen

@Composable
fun SmartAlertApp(homeViewModel: HomeViewModel = viewModel()) {

    // homeViewModel.checkForActiveSession()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
//        if (homeViewModel.isUserLoggedIn.value == true) {
//            HomeScreen()
//        } else {
//            WelcomeScreen()
//        }

        HomeScreen()
    }
}
