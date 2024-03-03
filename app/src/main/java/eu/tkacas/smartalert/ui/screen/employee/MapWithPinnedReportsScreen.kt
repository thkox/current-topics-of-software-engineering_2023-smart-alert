package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.app.SharedPrefManager
import eu.tkacas.smartalert.database.cloud.getAlertByPhenomenonAndLocationForMaps
import eu.tkacas.smartalert.database.cloud.getSpecificAlertByPhenomenonAndLocationForMaps
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.LocationData
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapWithPinnedReportsScreen(navController: NavController? = null){
    val sharedPrefManager = SharedPrefManager(LocalContext.current)
    val previousScreen = sharedPrefManager.getPreviousScreen()
    val scaffoldState = rememberScaffoldState()

    val weatherPhenomenon = sharedPrefManager.getCriticalWeatherPhenomenon()
    val criticalWeatherPhenomenon = CriticalWeatherPhenomenon.valueOf(weatherPhenomenon.name)

    val data = remember { mutableStateOf<List<LocationData>?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    var cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(LatLng(38.0742, 23.8243), 5.8f)
    }

    val currentLanguage = Locale.getDefault().language
    val title = if (currentLanguage == "el") {
        stringResource(id = R.string.map_title, stringResource(id = criticalWeatherPhenomenon.getStringId()))
    } else {
        stringResource(id = R.string.map_title, stringResource(id = criticalWeatherPhenomenon.getStringId()))
    }


    LaunchedEffect(key1 = criticalWeatherPhenomenon) {
        when (previousScreen) {
            "GroupEventsByLocationScreen" -> {
                getAlertByPhenomenonAndLocationForMaps(criticalWeatherPhenomenon.name) { success, result, err ->
                    if (success) {
                        data.value = result
                    } else {
                        error.value = err
                    }
                }
            }
            "EventsByLocationScreen" -> {
                val address = sharedPrefManager.getLocationID()
                getSpecificAlertByPhenomenonAndLocationForMaps(criticalWeatherPhenomenon.name, address) { success, result, err ->
                    if (success) {
                        data.value = result
                    } else {
                        error.value = err
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = title,
                navController = navController,
                enableSettingsButton = false
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ){
                data.value?.forEach { locationData ->
                    val position = LatLng(locationData.latitude, locationData.longitude)
                    Marker(state = MarkerState(position = position))
                }
            }
        }
    }
}


@Preview
@Composable
fun MapWithPinnedReportsScreenPreview(){
    MapWithPinnedReportsScreen()
}