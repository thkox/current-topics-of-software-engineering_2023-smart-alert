package eu.tkacas.smartalert.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.tkacas.smartalert.ui.navigation.Navigation


@Composable
fun SmartAlertApp() {

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Navigation()
    }
}