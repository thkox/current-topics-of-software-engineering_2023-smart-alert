package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.AlertWithImageDialog
import eu.tkacas.smartalert.ui.component.CardComponentWithImage
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.settings.MyReportsHistoryViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyReportsHistoryScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val viewModel = MyReportsHistoryViewModel()
    val alerts by viewModel.alerts.observeAsState(initial = emptyList())

    val showDialog = remember { mutableStateOf(false) }

    val selectedMessage = remember { mutableStateOf<String?>(null) }
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.my_reports), navController = navController)
        }
    ) {
        if (alerts.isEmpty()) {
            Text(text = "No reports found", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(alerts.size) { index ->
                    CardComponentWithImage(
                        row1 = alerts[index].timestamp,
                        row2 = alerts[index].message.toString(),
                        row3 = "${alerts[index].location.latitude}, ${alerts[index].location.longitude}",
                        image = alerts[index].criticalWeatherPhenomenon,
                        onClick = {
                            selectedMessage.value = alerts[index].message
                            selectedImageUrl.value = alerts[index].imageURL
                            showDialog.value = true
                        }
                    )
                }
            }
        }

        AlertWithImageDialog(
            showDialog = showDialog.value,
            message = selectedMessage.value,
            imageURL = selectedImageUrl.value,
            onDismiss = { showDialog.value = false }
        )
    }
}