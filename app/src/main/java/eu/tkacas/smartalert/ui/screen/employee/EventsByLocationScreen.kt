package eu.tkacas.smartalert.ui.screen.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.cloud.getSpecificAlertByPhenomenonAndLocation
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.ListOfSingleLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.ui.component.AlertWithImageDialog
import eu.tkacas.smartalert.ui.component.CardComponentWithImage
import eu.tkacas.smartalert.ui.component.ConfirmDeleteDialog
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.employee.EventsByLocationViewModel

@Composable
fun EventsByLocationScreen(navController: NavHostController? = null) {
    val sharedPrefManager = SharedPrefManager(LocalContext.current)
    sharedPrefManager.setPreviousScreen("EventsByLocationScreen")
    val scaffoldState = rememberScaffoldState()

    val viewModel = EventsByLocationViewModel()

    val showDialog = remember { mutableStateOf(false) }
    val showWarningDialog = remember { mutableStateOf(false) }
    val showMassWarningDialog = remember { mutableStateOf(false) }

    val selectedMessage = remember { mutableStateOf<String?>(null) }
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }

    val weatherPhenomenon = sharedPrefManager.getCriticalWeatherPhenomenon()
    val criticalWeatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenon.name)

    val locationID = sharedPrefManager.getLocationID()

    val data = remember { mutableStateOf<ListOfSingleLocationCriticalWeatherPhenomenonData?>(null) }

    val error = remember { mutableStateOf<String?>(null) }

    val isRefreshing = remember { mutableStateOf(false) }


    LaunchedEffect(key1 = criticalWeatherPhenomenon, key2 = locationID) {
        isRefreshing.value = true
        getSpecificAlertByPhenomenonAndLocation(criticalWeatherPhenomenon.name, locationID) { success, alertForms, areaBounds, areaName, err ->
            if (success) {
                data.value = alertForms

                sharedPrefManager.setBoundsNorthEastLat(areaBounds?.northeast?.lat)
                sharedPrefManager.setBoundsNorthEastLng(areaBounds?.northeast?.lng)
                sharedPrefManager.setBoundsSouthWestLat(areaBounds?.southwest?.lat)
                sharedPrefManager.setBoundsSouthWestLng(areaBounds?.southwest?.lng)

                sharedPrefManager.setLocationName(areaName)

            } else {
                error.value = err
            }
            isRefreshing.value = false
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = criticalWeatherPhenomenon.getStringId()), navController = navController)
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .size(40.dp),
                    containerColor = SkyBlue,
                    onClick = {
                        navController?.navigate("alertCitizensForm")
                    }
                ) {
                    Image(painterResource(id = R.drawable.add), contentDescription = "Map")
                }
                FloatingActionButton(
                    modifier = Modifier.padding(all = 15.dp),
                    containerColor = SkyBlue,
                    onClick = {
                        navController?.navigate("Map")
                    }
                ) {
                    Image(painterResource(id = R.drawable.map), contentDescription = "Map")
                }
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                onRefresh = {
                    isRefreshing.value = true
                    getSpecificAlertByPhenomenonAndLocation(criticalWeatherPhenomenon.name, locationID) { success, alertForms, locationBounds, locationName, err ->
                        if (success) {
                            data.value = alertForms

                            sharedPrefManager.setBoundsNorthEastLat(locationBounds?.northeast?.lat)
                            sharedPrefManager.setBoundsNorthEastLng(locationBounds?.northeast?.lng)
                            sharedPrefManager.setBoundsSouthWestLat(locationBounds?.southwest?.lat)
                            sharedPrefManager.setBoundsSouthWestLng(locationBounds?.southwest?.lng)

                            sharedPrefManager.setLocationName(locationName)

                        } else {
                            error.value = err
                        }
                        isRefreshing.value = false
                    }
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.The_reports_about_this_location_from_the_last_6_hours_),
                        //Text(text = "The reports about this location from the last 24 hours.",
                        color = PrussianBlue,
                        style = TextStyle(
                            color = PrussianBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        GeneralButtonComponent(
                            value = stringResource(id = R.string.delete_all),
                            //value = "Delete All",
                            onButtonClicked = {
                                showMassWarningDialog.value = true
                            }
                        )
                    }
                    if (data.value != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(data.value?.list?.size ?: 0) { index ->
                                CardComponentWithImage(
                                    row1 = data.value?.list?.get(index)?.location ?: "",
                                    row2 = stringResource(id = R.string.critical_level) + ": ${
                                        data.value?.list?.get(index)?.emLevel?.getStringId()
                                            ?.let { it1 -> stringResource(id = it1) }
                                    }",
                                    row3 = stringResource(id = R.string.time) + ": ${
                                        data.value?.list?.get(
                                            index
                                        )?.timeStamp
                                    }",
                                    beDeletedEnabled = true,
                                    onDelete = {
                                        showWarningDialog.value = true
                                    },
                                    onClick = {
                                        selectedMessage.value =
                                            data.value?.list?.get(index)?.message
                                        selectedImageUrl.value =
                                            data.value?.list?.get(index)?.imageURL
                                        showDialog.value = true

                                    }
                                )
                                AlertWithImageDialog(
                                    showDialog = showDialog.value,
                                    message = selectedMessage.value,
                                    imageURL = selectedImageUrl.value,
                                    onDismiss = { showDialog.value = false }
                                )
                                ConfirmDeleteDialog(
                                    showDialog = showWarningDialog.value,
                                    //title = "Warning",
                                    title = stringResource(id = R.string.Warning),
                                    //message = "You are about to delete an alert warning. Are you sure?",
                                    message = stringResource(id = R.string.You_are_about_to_delete_an_alert_warning_Are_you_sure),
                                    onDismiss = { showWarningDialog.value = false },
                                    onConfirm = {
                                        viewModel.deleteEventByPhenomenonAndLocation(
                                            criticalWeatherPhenomenon.name,
                                            locationID,
                                            data.value?.list?.get(index)?.alertID ?: ""
                                        )
                                        showWarningDialog.value = false
                                    }
                                )
                                ConfirmDeleteDialog(
                                    showDialog = showMassWarningDialog.value,
                                    title = stringResource(id = R.string.Warning),
                                    message = stringResource(id = R.string.You_are_about_to_delete_all_alert_warnings_Are_you_sure),
                                    onDismiss = { showMassWarningDialog.value = false },
                                    onConfirm = {
                                        viewModel.deleteAllEventsByPhenomenonAndLocation(
                                            criticalWeatherPhenomenon.name,
                                            locationID
                                        )
                                        showMassWarningDialog.value = false
                                    }
                                )
                            }
                        }
                    } else if (error.value != null) {
                        Text(stringResource(id = R.string.error) + ": ${error.value}")
                    }
                }
            }
        }
    }
}

fun getCriticalLevelFromRow(row2: String): String {
    // Split the row2 string by ": " and get the second part which is the critical level
    val parts = row2.split(": ")
    return if (parts.size > 1) parts[1] else "Unknown"
}

@Preview
@Composable
fun EventsByLocationScreenPreview(){
    EventsByLocationScreen()
}