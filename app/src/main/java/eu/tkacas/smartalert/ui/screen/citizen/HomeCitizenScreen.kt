package eu.tkacas.smartalert.ui.screen.citizen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.permissions.areAllPermissionsGranted
import eu.tkacas.smartalert.ui.navigation.AppBarWithoutBackView
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeCitizenScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarWithoutBackView(title = stringResource(id = R.string.home), navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
                    navController?.navigate("alertForm")
                }
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Card (
            modifier = Modifier.padding(16.dp)

        ) {
            Text("Home Citizen Screen")
        }
    }
}


@Preview
@Composable
fun HomeCitizenScreenPreview() {
    HomeCitizenScreen()
}