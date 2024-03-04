package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.SettingCard
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.screen.screensInSettings
import eu.tkacas.smartalert.viewmodel.settings.SettingsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel = SettingsViewModel(LocalContext.current)
    val sharedPrefManager = viewModel.sharedPrefManager

    Scaffold(
        topBar = {
            AppBarBackView(
                title = stringResource(id = R.string.settings),
                navController = navController,
                enableSettingsButton = false
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(screensInSettings) { screen ->
                    if (!(sharedPrefManager.isEmployee() && screen.route == "MyReportsHistory")) {
                        SettingCard(screen = screen) {
                            navController.navigate(screen.route)
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}