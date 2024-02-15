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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.CriticalWeatherPhenomenonButtonComponent
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeCitizenScreen() {

    val title = stringResource(id = R.string.app_name)

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()


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
                            //change to settings activity
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
    HomeCitizenScreen()
}