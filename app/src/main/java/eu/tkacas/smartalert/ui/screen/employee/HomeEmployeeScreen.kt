package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.CriticalWeatherPhenomenonCardComponent
import eu.tkacas.smartalert.ui.component.HistoryMessageCard
import eu.tkacas.smartalert.ui.component.NotificationsHistoryDialog
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.ui.theme.UTOrange
import eu.tkacas.smartalert.viewmodel.employee.HomeEmployeeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeEmployeeScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val viewModel = HomeEmployeeViewModel(LocalContext.current)

    val showDialog = remember { mutableStateOf(false) }

    val selectedWeatherPhenomenon = remember { mutableStateOf<String?>(null) }
    val selectedDateTime = remember { mutableStateOf<String?>(null) }
    val selectedLocation = remember { mutableStateOf<String?>(null) }
    val selectedMessage = remember { mutableStateOf<String?>(null) }

    val firstNameVal by viewModel.firstNameVal.collectAsState()
    val data by viewModel.data.collectAsState()
    val errorM by viewModel.errorM.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = "${stringResource(id = R.string.hello)}, $firstNameVal",
                navController = navController,
                enableBackButton = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                containerColor = SkyBlue,
                onClick = {
                    navController?.navigate("alertCitizensForm")
                }
            ) {
                Image(painter = painterResource(id = R.drawable.add), contentDescription = null)
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                LazyVerticalGrid(
                    GridCells.Fixed(2)
                ) {
                    items(CriticalWeatherPhenomenon.entries.size) { index ->
                        CriticalWeatherPhenomenonCardComponent(
                            navController,
                            CriticalWeatherPhenomenon.entries[index]
                        )
                    }
                }
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    viewModel.fetchData()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            text = stringResource(id = R.string.Notifications_history),
                            color = PrussianBlue,
                            style = TextStyle(
                                color = PrussianBlue,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        if (data != null) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(data?.list?.size ?: 0) { index ->
                                    val weatherPhenomenonString =
                                        data?.list?.get(index)?.weatherPhenomenon?.let {
                                            stringResource(it.getStringId())
                                        } ?: ""
                                    HistoryMessageCard(
                                        weatherPhenomenonText = weatherPhenomenonString,
                                        locationText = data?.list?.get(index)?.locationName ?: "",
                                        dateTimeText = data?.list?.get(index)?.messageTime ?: "",
                                        onClick = {
                                            selectedWeatherPhenomenon.value = weatherPhenomenonString
                                            selectedDateTime.value =
                                                data?.list?.get(index)?.messageTime
                                            selectedLocation.value =
                                                data?.list?.get(index)?.locationName
                                            selectedMessage.value =
                                                data?.list?.get(index)?.message
                                            showDialog.value = true
                                        },
                                        color = data?.list?.get(index)?.criticalLevel?.getColor()
                                            ?.let { colorResource(id = it) }
                                            ?: colorResource(id = R.color.colorWhite)
                                    )
                                }
                            }
                            NotificationsHistoryDialog(
                                showDialog = showDialog.value,
                                weatherPhenomenonText = selectedWeatherPhenomenon.value ?: "",
                                locationText = selectedLocation.value ?: "",
                                dateTimeText = selectedDateTime.value ?: "",
                                messageText = selectedMessage.value ?: "",
                                onDismiss = { showDialog.value = false }
                            )
                        } else {
                            Text(
                                text = errorM ?: "",
                                color = UTOrange
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeEmployeeScreenPreview() {
    HomeEmployeeScreen()
}