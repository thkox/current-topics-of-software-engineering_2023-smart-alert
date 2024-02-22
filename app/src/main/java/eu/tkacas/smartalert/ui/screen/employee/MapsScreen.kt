package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import androidx.navigation.NavController
import com.google.maps.android.compose.rememberCameraPositionState
import eu.tkacas.smartalert.ui.navigation.AppBarBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapsScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    var cameraPositioState = rememberCameraPositionState{
        mutableStateOf(LatLng(39.0742, 21.8243))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = "Wildfire's Map", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositioState
            ){

            }
        }
    }
}

@Preview
@Composable
fun MapsScreenPreview() {
    MapsScreen()
}