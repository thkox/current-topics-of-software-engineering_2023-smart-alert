package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.navigation.AppBarWithoutBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyReportsHistoryScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = "Reports History", navController = navController)
        }
    ) {
        Card(
            modifier = Modifier.padding(16.dp)

        ) {
            Text("Home Citizen Screen")
        }
    }
}



@Preview
@Composable
fun MyReportsHistoryPreview(){
    MyReportsHistoryScreen()
}