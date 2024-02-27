package eu.tkacas.smartalert.ui.screen.employee

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.AlertLevelButtonsRowComponent
import eu.tkacas.smartalert.ui.component.CityTextFieldComponent
import eu.tkacas.smartalert.ui.component.EnumDropdownComponent
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.employee.AlertCitizensFormViewModel
import kotlinx.coroutines.launch

@Composable
fun AlertCitizensFormScreen(navController: NavHostController? = null){
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val viewModel = AlertCitizensFormViewModel()

    val placesAPI = viewModel.createPlacesAPI()

    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.alert_form), navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.size(80.dp))
                NormalTextComponent(value = stringResource(id = R.string.city_of_emergency))
                CityTextFieldComponent(
                    labelValue = stringResource(id = R.string.city),
                    placesAPI = placesAPI,
                    apiKey = "AIzaSyBM31FS8qWSsNewQM5NGzpYm7pdr8q5azY",
                    onTextChanged = {
                        viewModel.setSelectedArea(it)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                NormalTextComponent(value = stringResource(id = R.string.weather_phenomenon_selection))
                EnumDropdownComponent(
                    CriticalWeatherPhenomenon::class.java,
                    initialSelection = viewModel.selectedWeatherPhenomenon.value,
                    onSelected = {
                        viewModel.setSelectedWeatherPhenomenon(it)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                NormalTextComponent(value = stringResource(id = R.string.emergency_level))
                AlertLevelButtonsRowComponent(
                    initialValue = viewModel.selectedDangerLevelButton.value,
                    onButtonClicked = {
                        viewModel.setSelectedDangerLevelButton(it)
                    }
                )
                Spacer(modifier = Modifier.size(80.dp))
                GeneralButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onButtonClicked = {
                        scope.launch {
                            viewModel.sendAlertToCitizens()
                            navController?.navigate("home")
                            Toast.makeText(context, "Alert to citizens sent successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun AlertCitizensFormScreenPreview(){
    AlertCitizensFormScreen()
}

