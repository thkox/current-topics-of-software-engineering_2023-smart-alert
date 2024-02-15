package eu.tkacas.smartalert.ui.navigation

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R

@Composable
fun AppBarBackView(
    title: String,
    navController: NavController? = null,
){
    TopAppBar(
        title = {
            Text(text = title,
                color = colorResource(id = R.color.colorWhite),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp))
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.prussian_blue),
        navigationIcon = {
            IconButton(onClick = { navController?.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun AppBarWithoutBackView(
    title: String,
    navController: NavController? = null
){

    TopAppBar(
        title = {
            Text(text = title,
                color = colorResource(id = R.color.colorWhite),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp))
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.prussian_blue),
        navigationIcon = null,
        actions = {
            IconButton(
                onClick = {
                    navController?.navigate("settings")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = null
                )
            }
        }

    )
}