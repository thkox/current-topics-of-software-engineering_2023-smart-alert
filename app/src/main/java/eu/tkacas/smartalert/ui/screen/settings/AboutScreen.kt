package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.ButtonWithImageComponent
import eu.tkacas.smartalert.ui.component.CenteredAboutText
import eu.tkacas.smartalert.ui.component.DividerComponentWithoutText
import eu.tkacas.smartalert.ui.component.VerticalDividerComponentWithoutText
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.PrussianBlue

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val config = LocalConfiguration.current

    val portraitMode = remember { mutableStateOf(config.orientation ) }

    if (portraitMode.value == Configuration.ORIENTATION_PORTRAIT) {
        //PortraitLayout()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBarBackView(title = stringResource(id = R.string.about), navController = navController)
            }
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        horizontalArrangement = Arrangement.Absolute.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painterResource(id = R.drawable.smart_alert_logo_full_transparent), contentDescription = "Logo of the Smart Alert App")
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                            ){
                                CenteredAboutText()
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth())
                            //horizontalArrangement = Arrangement.Center,
                            //verticalAlignment = Alignment.CenterVertically)
                            {
                                DividerComponentWithoutText()
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(modifier = Modifier.fillMaxWidth()){
                                Text(
                                    text = stringResource(id = R.string.connect_with_us),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = PrussianBlue
                                )
                            }
                        }
                    }
                }
                //add GitHub Links
                Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                    val context = LocalContext.current

                    val url1 = remember { Uri.parse("https://github.com/thkox") }
                    ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url1)) }, imageId = R.drawable.github_logo, buttonText = "thkox")

                    val url2 = remember { Uri.parse("https://github.com/Apostolis2002") }
                    ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url2)) }, imageId = R.drawable.github_logo, buttonText = "ApostolisSiampanis")

                    val url3 = remember { Uri.parse("https://github.com/AlexanderCholis") }
                    ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url3)) }, imageId = R.drawable.github_logo, buttonText = "AlexanderCholis")
                }
            }
        }
    } else {
        //LandscapeLayout()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBarBackView(title = stringResource(id = R.string.about), navController = navController)
            }
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(top = 2.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Image(painterResource(id = R.drawable.smart_alert_logo_full_transparent), contentDescription = "Logo of the Smart Alert App")
                            Spacer(modifier = Modifier.height(20.dp))
                            CenteredAboutText()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    VerticalDividerComponentWithoutText()
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                            Text(
                                text = stringResource(id = R.string.connect_with_us),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = PrussianBlue
                            )

                            val context = LocalContext.current

                            val url1 = remember { Uri.parse("https://github.com/thkox") }
                            ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url1)) }, imageId = R.drawable.github_logo, buttonText = "thkox")

                            val url2 = remember { Uri.parse("https://github.com/Apostolis2002") }
                            ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url2)) }, imageId = R.drawable.github_logo, buttonText = "ApostolisSiampanis")

                            val url3 = remember { Uri.parse("https://github.com/AlexanderCholis") }
                            ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url3)) }, imageId = R.drawable.github_logo, buttonText = "AlexanderCholis")

                    }
                }
            }
        }

    }

//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            AppBarBackView(title = stringResource(id = R.string.about), navController = navController)
//        }
//    ) {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(15.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 2.dp),
//                    horizontalArrangement = Arrangement.Absolute.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(painterResource(id = R.drawable.smart_alert_logo_full_transparent), contentDescription = "Logo of the Smart Alert App")
//                }
//                Row (
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//
//                ) {
//                    Column {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 20.dp),
//                        ){
//                            CenteredAboutText()
//                        }
//                        Spacer(modifier = Modifier.height(10.dp))
//                        Row(modifier = Modifier.fillMaxWidth())
//                            //horizontalArrangement = Arrangement.Center,
//                            //verticalAlignment = Alignment.CenterVertically)
//                        {
//                            DividerComponentWithoutText()
//                        }
//                        Spacer(modifier = Modifier.height(15.dp))
//                        Row(modifier = Modifier.fillMaxWidth()){
//                                Text(
//                                    text = stringResource(id = R.string.connect_with_us),
//                                    modifier = Modifier.fillMaxWidth(),
//                                    textAlign = TextAlign.Center,
//                                    color = PrussianBlue
//                                )
//                        }
//                    }
//                }
//            }
//            //add GitHub Links
//            Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
//                val context = LocalContext.current
//
//                val url1 = remember { Uri.parse("https://github.com/thkox") }
//                ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url1)) }, imageId = R.drawable.github_logo, buttonText = "thkox")
//
//                val url2 = remember { Uri.parse("https://github.com/Apostolis2002") }
//                ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url2)) }, imageId = R.drawable.github_logo, buttonText = "ApostolisSiampanis")
//
//                val url3 = remember { Uri.parse("https://github.com/AlexanderCholis") }
//                ButtonWithImageComponent(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, url3)) }, imageId = R.drawable.github_logo, buttonText = "AlexanderCholis")
//            }
//        }
//    }
}




@Preview
@Composable
fun AboutScreenPreview(){
    AboutScreen()
}