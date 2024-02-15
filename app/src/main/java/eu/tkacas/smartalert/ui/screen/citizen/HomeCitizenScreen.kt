package eu.tkacas.smartalert.ui.screen.citizen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeCitizenScreen(navController: NavController) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    //Allow us to find out on which screen we are
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Update the title based on the current route
    val title = when (currentRoute) {
        "settings" -> "Settings"
        else -> ""
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = {
                        //do nothing
                    }) {
                        Icon(painter = painterResource(id = R.drawable.crisis_alert), contentDescription = null)
                    }
                },
                actions = {
                    androidx.compose.material3.IconButton(
                        onClick = {
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                },
            )
        }
    ) {
        Text(text ="Test", modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun HomeCitizenScreenPreview() {
    HomeCitizenScreen(navController = rememberNavController())
}