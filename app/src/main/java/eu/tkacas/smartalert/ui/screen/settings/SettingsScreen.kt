package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import kotlinx.coroutines.Dispatchers
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.cloud.CloudFunctionsUtils
import eu.tkacas.smartalert.ui.component.SettingCard
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.screen.screensInSettings
import eu.tkacas.smartalert.viewmodel.settings.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.checkerframework.checker.units.qual.Current

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel = SettingsViewModel(LocalContext.current)
    val sharedPrefManager = viewModel.sharedPrefManager

    Scaffold(
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.settings), navController = navController, enableSettingsButton = false)
        },
        content = {
            LazyColumn (
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