package eu.tkacas.smartalert.ui.screen.employee

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.component.CriticalWeatherPhenomenonCardComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.employee.HomeEmployeeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeEmployeeScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val viewModel = HomeEmployeeViewModel(LocalContext.current)

    val firstNameVal = viewModel.firstNameVal

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
    }
}

@Preview
@Composable
fun HomeEmployeeScreenPreview() {
    HomeEmployeeScreen()
}