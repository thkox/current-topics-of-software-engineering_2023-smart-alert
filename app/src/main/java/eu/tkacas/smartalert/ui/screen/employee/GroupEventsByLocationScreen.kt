package eu.tkacas.smartalert.ui.screen.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import eu.tkacas.smartalert.cloud.getAlertByPhenomenonAndLocation
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.ListOfLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.ui.component.CardComponentWithImage
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.BlueGreen
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.ui.theme.UTOrange

@Composable
fun GroupEventsByLocationScreen(navController: NavHostController? = null){
    val sharedPrefManager = SharedPrefManager(LocalContext.current)
    sharedPrefManager.setPreviousScreen("GroupEventsByLocationScreen")
    val scaffoldState = rememberScaffoldState()

    val weatherPhenomenon = sharedPrefManager.getCriticalWeatherPhenomenon()
    val criticalWeatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenon.name)

    val data = remember { mutableStateOf<ListOfLocationCriticalWeatherPhenomenonData?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    val isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = criticalWeatherPhenomenon) {
        isRefreshing.value = true
        getAlertByPhenomenonAndLocation(criticalWeatherPhenomenon.name) { success, result, err ->
            if (success) {
                data.value = result
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
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                containerColor = SkyBlue,
                onClick = {
                    navController?.navigate("Map")
                }
            ){
                Image(painterResource(id = R.drawable.map), contentDescription = "Map")
            }
        }
    ) { it ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
            onRefresh = {
                isRefreshing.value = true
                getAlertByPhenomenonAndLocation(criticalWeatherPhenomenon.name) { success, result, err ->
                    if (success) {
                        data.value = result
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
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.The_reports_history_from_the_last_24_hours_),
                        //Text(text = "The reports history from the last 24 hours.",
                        color = PrussianBlue,
                        style = TextStyle(
                            color = PrussianBlue,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    if (data.value != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(data.value?.list?.size ?: 0) { index ->
                                CardComponentWithImage(
                                    row1 = data.value?.list?.get(index)?.location ?: "",
                                    row2 = stringResource(id = R.string.number_of_reports) + ": ${
                                        data.value?.list?.get(
                                            index
                                        )?.numOfReports ?: 0
                                    }",
                                    beDeletedEnabled = false,
                                    onClick = {
                                        sharedPrefManager.setAddress(
                                            data.value?.list?.get(index)?.location ?: ""
                                        )
                                        navController?.navigate("EventsByLocation")
                                    }
                                )
                            }
                        }
                    } else if (error.value != null) {
                        //Text("Error: ${error.value}")
                        Text(
                            stringResource(id = R.string.error) + ": ${error.value}",
                            color = UTOrange
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun GroupEventsByLocationScreenPreview(){
    GroupEventsByLocationScreen()
}