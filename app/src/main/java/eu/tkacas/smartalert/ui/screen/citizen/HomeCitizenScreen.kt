package eu.tkacas.smartalert.ui.screen.citizen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.database.cloud.getFirstName
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.SkyBlue

@Composable
fun HomeCitizenScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    var firstNameVal: String by remember { mutableStateOf("") }

    getFirstName { success, firstName, error ->
        if (success) {
            firstNameVal = firstName ?: ""
        } else {
            println("Error occurred: $error")
        }
    }

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
                    navController?.navigate("alertForm")
                }
            ) {
                Image(painterResource(id = R.drawable.add), contentDescription = null)
            }
        }
    ) {
        Card(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                /*if() {
                    items() { index ->

                    }
                } else if () {
                    Text(text = stringResource(),
                        color = UTOrange)
                }*/
            }
        }
    }
}


@Preview
@Composable
fun HomeCitizenScreenPreview() {
    HomeCitizenScreen()
}