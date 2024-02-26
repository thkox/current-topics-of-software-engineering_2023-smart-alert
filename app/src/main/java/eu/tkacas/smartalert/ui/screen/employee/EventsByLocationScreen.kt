package eu.tkacas.smartalert.ui.screen.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.cloud.getSpecificAlertByPhenomenonAndLocation
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.ListOfSingleLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.ui.component.AlertWithImageDialog
import eu.tkacas.smartalert.ui.component.CardComponentWithImage
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.employee.EventsByLocationViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EventsByLocationScreen(navController: NavHostController? = null) {
    val sharedPrefManager = SharedPrefManager(LocalContext.current)
    sharedPrefManager.setPreviousScreen("EventsByLocationScreen")
    val scaffoldState = rememberScaffoldState()

    val viewModel = EventsByLocationViewModel()

    val showDialog = remember { mutableStateOf(false) }
    val selectedMessage = remember { mutableStateOf<String?>(null) }
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }

    val weatherPhenomenon = sharedPrefManager.getCriticalWeatherPhenomenon()
    val criticalWeatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenon.name)

    val address = sharedPrefManager.getAddress()

    val data = remember { mutableStateOf<ListOfSingleLocationCriticalWeatherPhenomenonData?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = criticalWeatherPhenomenon, key2 = address) {
        getSpecificAlertByPhenomenonAndLocation(criticalWeatherPhenomenon.name, address) { success, result, err ->
            if (success) {
                data.value = result
            } else {
                error.value = err
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = "Specific " + stringResource(id = criticalWeatherPhenomenon.getStringId()), navController = navController)
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .size(40.dp), // This will make the button smaller
                    contentColor = Color.White,
                    backgroundColor = Color.Black,
                    onClick = {
                        navController?.navigate("alertCitizensForm")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
                FloatingActionButton(
                    modifier = Modifier.padding(all = 15.dp),
                    contentColor = Color.White,
                    backgroundColor = Color.Black,
                    onClick = {
                        navController?.navigate("Map")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Map, contentDescription = "Map")
                }
            }
        }
    ){ it ->
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
                Text(text = "The reports about this location from the last 24 hours.",
                    color = Color.Black,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ){
                    GeneralButtonComponent(
                        value = "Delete All",
                        onButtonClicked = {
                            viewModel.deleteAllEventsByPhenomenonAndLocation(criticalWeatherPhenomenon.name, address)
                        }
                    )
                }
                if (data.value != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(data.value?.list?.size ?: 0) { index ->
                            // TODO: Convert timestamp in cloud function
                            val timestamp = data.value?.list?.get(index)?.timeStamp ?: 0
                            val instant = Instant.ofEpochMilli(timestamp)
                            val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
                            val time = formatter.format(instant)
                            CardComponentWithImage(
                                row1 = data.value?.list?.get(index)?.location ?: "",
                                row2 = "Critical Level: ${data.value?.list?.get(index)?.emLevel.toString()}",
                                row3 = "Time: $time",
                                beDeletedEnabled = true,
                                onClick = {
                                    selectedMessage.value = data.value?.list?.get(index)?.message
                                    selectedImageUrl.value = data.value?.list?.get(index)?.imageURL
                                    showDialog.value = true

                                }
                            )
                            if (showDialog.value) {
                                AlertWithImageDialog(
                                    message = selectedMessage.value,
                                    imageURL = selectedImageUrl.value,
                                    onDismiss = { showDialog.value = false }
                                )
                            }
                        }
                    }
                } else if (error.value != null) {
                    Text("Error: ${error.value}")
                }
            }
        }
    }
}

@Preview
@Composable
fun EventsByLocationScreenPreview(){
    EventsByLocationScreen()
}