package eu.tkacas.smartalert.ui.screen.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.ui.component.CardComponentWithImage
import eu.tkacas.smartalert.ui.navigation.AppBarBackView

@Composable
fun GroupEventsByLocationScreen(navController: NavHostController? = null){
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    // TODO: create a val that loads the data from the db using a fun from the viewModel


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = "Wildfire appeared!", navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
                    navController?.navigate("Map")
                }
            ){
                Icon(imageVector = Icons.Default.Map, contentDescription = null)
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(5) {
                        // TODO: import the values from the database (in a list)
                        CardComponentWithImage()
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun GroupEventsByLocationScreenPreview(){
    GroupEventsByLocationScreen()
}