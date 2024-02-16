package eu.tkacas.smartalert.ui.screen.citizen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun AlertFormScreen(navController: NavHostController? = null) {
    Text(text = "Alert Form Screen")
}


@Preview
@Composable
fun AlertFormScreenPreview(){
    AlertFormScreen()
}