package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.CriticalWeatherPhenomenonCardComponent
import eu.tkacas.smartalert.ui.navigation.AppBarWithoutBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeEmployeeScreen(navController: NavController? = null){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarWithoutBackView(title = "Home Employee Screen", navController = navController)
        }
    ) {
        Box(
            modifier = Modifier.padding(top = 20.dp)
        ){
            LazyVerticalGrid(
                GridCells.Fixed(2)
            ) {
                items(CriticalWeatherPhenomenon.values().size) {index ->
                    CriticalWeatherPhenomenonCardComponent(CriticalWeatherPhenomenon.values()[index])
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeEmployeeScreenPreview(){
    HomeEmployeeScreen()
}