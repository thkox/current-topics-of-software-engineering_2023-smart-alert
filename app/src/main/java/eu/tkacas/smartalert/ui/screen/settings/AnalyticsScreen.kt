package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.Months
import eu.tkacas.smartalert.ui.component.EnumDropdownComponentMonths
import eu.tkacas.smartalert.ui.component.PieChart
import eu.tkacas.smartalert.ui.component.YearDropdownComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.settings.AnalyticsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AnalyticsScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val viewModel = AnalyticsViewModel(LocalContext.current)

    // Create a list of years and a list of months based on the fetched data
    val years by viewModel.years.observeAsState(listOf())

    // Observe the LiveData objects in your ViewModel
    val earthquakeCount by viewModel.earthquakeCount.observeAsState(0)
    val floodCount by viewModel.floodCount.observeAsState(0)
    val wildfireCount by viewModel.wildfireCount.observeAsState(0)
    val riverFloodCount by viewModel.riverFloodCount.observeAsState(0)
    val heatwaveCount by viewModel.heatwaveCount.observeAsState(0)
    val snowstormCount by viewModel.snowstormCount.observeAsState(0)
    val stormCount by viewModel.stormCount.observeAsState(0)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.analytics), navController = navController)
        }
    ) {
        if (years.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                YearDropdownComponent(
                    years = years,
                    initialSelection = years.first(),
                    onSelected = { selectedYear ->
                        viewModel.selectedYear = selectedYear
                        viewModel.fetchStatisticsPerMonth()
                    }
                )

                EnumDropdownComponentMonths(
                    enumClass = Months::class.java,
                    initialSelection = Months.Select_Month, // or any other initial month
                    onSelected = { selectedMonth ->
                        // handle the selected month here
                        viewModel.selectedMonth = selectedMonth.toString()
                        viewModel.fetchStatisticsPerMonth()
                    }
                )

                PieChart(
                    data = mapOf(
                        Pair(stringResource(R.string.earthquake), earthquakeCount),
                        Pair(stringResource(R.string.flood), floodCount),
                        Pair(stringResource(R.string.wildfire), wildfireCount),
                        Pair(stringResource(R.string.river_flood), riverFloodCount),
                        Pair(stringResource(R.string.heatwave), heatwaveCount),
                        Pair(stringResource(R.string.snowstorm), snowstormCount),
                        Pair(stringResource(R.string.storm), stormCount),
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun AnalyticsScreenPreview(){
    AnalyticsScreen()
}