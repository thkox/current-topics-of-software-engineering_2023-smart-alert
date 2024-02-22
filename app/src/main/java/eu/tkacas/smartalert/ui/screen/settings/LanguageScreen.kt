package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.permissions.openAppSettings
import eu.tkacas.smartalert.ui.component.LanguageCard
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.navigation.AppBarWithoutBackView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LanguageScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val activity = context as Activity

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = stringResource(id = R.string.language), navController = navController)
        }
    ) {
        Column{
            LanguageCard(
                onClick = {
                    openAppSettings(context)
                },
                imageResId = R.drawable.usa_flag,
                textResId = R.string.english_language)

            LanguageCard(
                onClick = {
                    openAppSettings(context)
                },
                imageResId = R.drawable.greece_flag,
                textResId = R.string.greek_language)
        }
    }
}



@Preview
@Composable
fun LanguageScreenPreview(){
    LanguageScreen()
}