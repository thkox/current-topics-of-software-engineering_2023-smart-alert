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
fun HomeCitizenScreen() {

    Text(text ="Test")
}

@Preview
@Composable
fun HomeCitizenScreenPreview() {
    HomeCitizenScreen()
}