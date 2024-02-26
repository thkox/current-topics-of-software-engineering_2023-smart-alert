package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.CriticalWeatherPhenomenonCardComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeEmployeeScreen(navController: NavController? = null){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.home), navController = navController, enableBackButton = false)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                onClick = {
                    navController?.navigate("alertCitizensForm")
                }
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(top = 20.dp)
        ){
            LazyVerticalGrid(
                GridCells.Fixed(2)
            ) {
                items(CriticalWeatherPhenomenon.entries.size) { index ->
                    CriticalWeatherPhenomenonCardComponent(navController, CriticalWeatherPhenomenon.entries[index])
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