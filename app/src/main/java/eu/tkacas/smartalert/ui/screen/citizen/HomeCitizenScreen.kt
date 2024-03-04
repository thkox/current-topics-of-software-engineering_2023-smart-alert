package eu.tkacas.smartalert.ui.screen.citizen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import eu.tkacas.smartalert.database.cloud.getFirstName
import eu.tkacas.smartalert.database.local.DatabaseHelper
import eu.tkacas.smartalert.models.ListOfHistoryMessages
import eu.tkacas.smartalert.ui.component.HistoryMessageCard
import eu.tkacas.smartalert.ui.component.NotificationsHistoryDialog
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.ui.theme.UTOrange

@Composable
fun HomeCitizenScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)

    val showDialog = remember { mutableStateOf(false) }

    val selectedWeatherPhenomenon = remember { mutableStateOf<String?>(null) }
    val selectedDateTime = remember { mutableStateOf<String?>(null) }
    val selectedLocation = remember { mutableStateOf<String?>(null) }
    val selectedMessage = remember { mutableStateOf<String?>(null) }

    val data = remember { mutableStateOf<ListOfHistoryMessages?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    val isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = data.value) {
        isRefreshing.value = true
        databaseHelper.getMessages() { success, result, err ->
            if (success) {
                data.value = result
            } else {
                error.value = err
            }
            isRefreshing.value = false
        }
    }

    var firstNameVal: String by remember { mutableStateOf("") }

    getFirstName { success, firstName, error ->
        if (success) {
            firstNameVal = firstName ?: ""
        } else {
            println("Error occurred: $error")
        }
    }

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
                    navController?.navigate("alertForm")
                }
            ) {
                Image(painterResource(id = R.drawable.add), contentDescription = null)
            }
        }
    ) { it ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
            onRefresh = {
                isRefreshing.value = true
                databaseHelper.getMessages() { success, result, err ->
                    if (success) {
                        data.value = result
                    } else {
                        error.value = err
                    }
                    isRefreshing.value = false
                }
            }
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
                        text = stringResource(id = R.string.Notifications_history),
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
                                val weatherPhenomenonString =
                                    data.value?.list?.get(index)?.weatherPhenomenon?.let {
                                        stringResource(it.getStringId())
                                    } ?: ""
                                HistoryMessageCard(
                                    weatherPhenomenonText = weatherPhenomenonString,
                                    locationText = data.value?.list?.get(index)?.locationName ?: "",
                                    dateTimeText = data.value?.list?.get(index)?.messageTime ?: "",
                                    onClick = {
                                        selectedWeatherPhenomenon.value = weatherPhenomenonString
                                        selectedDateTime.value =
                                            data.value?.list?.get(index)?.messageTime
                                        selectedLocation.value =
                                            data.value?.list?.get(index)?.locationName
                                        selectedMessage.value =
                                            data.value?.list?.get(index)?.message
                                        showDialog.value = true
                                    },
                                    color = data.value?.list?.get(index)?.criticalLevel?.getColor()
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
                    } else if (error != null) {
                        Text(
                            text = stringResource(id = R.string.error) + ": ${error.value}",
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
fun HomeCitizenScreenPreview() {
    HomeCitizenScreen()
}