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
    val viewModel = AnalyticsViewModel()

    // Fetch the data from the backend when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    // Create a list of years and a list of months based on the fetched data
    val years by viewModel.years.observeAsState(listOf())
    val months by viewModel.months.observeAsState(listOf())

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
                    }
                )
                EnumDropdownComponentMonths(
                    enumClass = Months::class.java,
                    initialSelection = Months.JANUARY, // or any other initial month
                    onSelected = { selectedMonth ->
                        // handle the selected month here
                        viewModel.selectedMonth = selectedMonth.toString()
                    }
                )
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
}

@Preview
@Composable
fun AnalyticsScreenPreview(){
    AnalyticsScreen()
}