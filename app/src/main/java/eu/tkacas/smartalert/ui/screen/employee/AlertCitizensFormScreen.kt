package eu.tkacas.smartalert.ui.screen.employee

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.CityTextFieldComponent
import eu.tkacas.smartalert.ui.component.CriticalLevelButtonsRowComponent
import eu.tkacas.smartalert.ui.component.EnumDropdownComponent
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.component.UploadButtonComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.employee.AlertCitizensFormViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun AlertCitizensFormScreen(navController: NavHostController? = null) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val viewModel = AlertCitizensFormViewModel(context)
    val sharedPrefManager = SharedPrefManager(context)

    val placesAPI = viewModel.createPlacesAPI()

    val scope = rememberCoroutineScope()

    val locationName = remember { mutableStateOf(sharedPrefManager.getLocationName()) }

    val criticalWeatherPhenomenon =
        remember { mutableStateOf(sharedPrefManager.getCriticalWeatherPhenomenon()) }

    if (locationName.value != "")
        viewModel.setSelectedWeatherPhenomenon(criticalWeatherPhenomenon.value)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = stringResource(id = R.string.alert_form),
                navController = navController,
                enableSettingsButton = false
            )
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(80.dp))
                        NormalTextComponent(value = stringResource(id = R.string.city_of_emergency))
                        CityTextFieldComponent(
                            labelValue = stringResource(id = R.string.city),
                            placesAPI = placesAPI,
                            apiKey = "API_KEY_GC",
                            locationName = locationName
                        ) {
                            viewModel.setSelectedArea(it)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        NormalTextComponent(value = stringResource(id = R.string.weather_phenomenon_selection))
                        EnumDropdownComponent(
                            CriticalWeatherPhenomenon::class.java,
                            initialSelection = criticalWeatherPhenomenon.value,
                            onSelected = {
                                viewModel.setSelectedWeatherPhenomenon(it)
                            },
                            enabled = locationName.value == ""
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        NormalTextComponent(value = stringResource(id = R.string.emergency_level))
                        CriticalLevelButtonsRowComponent(
                            initialValue = viewModel.selectedDangerLevelButton.value,
                            onButtonClicked = {
                                viewModel.setSelectedDangerLevelButton(it)
                            }
                        )
                        Spacer(modifier = Modifier.size(80.dp))
                        UploadButtonComponent(
                            value = stringResource(id = R.string.submit),
                            onButtonClicked = {
                                scope.launch {
                                    viewModel.sendAlertToCitizens()
                                    navController?.navigate("home")
                                    val currentLanguage = Locale.getDefault().language
                                    val toastAlertCitizensMessage = when (currentLanguage) {
                                        "en" -> "Alert to citizens sent successfully"
                                        "el" -> "Η ειδοποίηση προς τους πολίτες στάλθηκε επιτυχώς"
                                        else -> "Alert to citizens sent successfully"
                                    }
                                    Toast.makeText(
                                        context,
                                        toastAlertCitizensMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }
            }
        }

    }
}


@Preview
@Composable
fun AlertCitizensFormScreenPreview() {
    AlertCitizensFormScreen()
}

