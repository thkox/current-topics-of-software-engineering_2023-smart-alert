package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.PieChart
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.navigation.AppBarWithoutBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AnalyticsScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.analytics), navController = navController)
        }
    ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                // Preview with sample data - Should change to real data from the database
                PieChart(
                    data = mapOf(
                        Pair(stringResource(R.string.earthquake), 150),
                        Pair(stringResource(R.string.flood), 40),
                        Pair(stringResource(R.string.wildfire), 110),
                        Pair(stringResource(R.string.river_flood), 10),
                        Pair(stringResource(R.string.heatwave), 8),
                        Pair(stringResource(R.string.snowstorm), 120),
                        Pair(stringResource(R.string.storm), 132),
                    )
                )
            }

    }
}



@Preview
@Composable
fun AnalyticsScreenPreview(){
    AnalyticsScreen()
}