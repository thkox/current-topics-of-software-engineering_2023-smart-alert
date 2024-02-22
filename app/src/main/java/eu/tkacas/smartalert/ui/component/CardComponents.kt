package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.screen.Screen


@Composable
fun CardComponent(iconResId: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                //painter = painterResource(id = R.drawable.wildfire),
                painter = painterResource(id = iconResId),
                contentDescription = "Card Icon",
                modifier = Modifier.size(34.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "15/02/2024")
                Text(text = "Kifissia, Athens")
                Text(text = "Number of Events: 3")
            }

        }
    }
}

@Preview
@Composable
fun CardComponentWithImage(
    address: String = "Kifissia, Athens",
    emLevel: String = "Emergency level",
    message: String = "Tap to show the message",
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {

        },
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){
            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ){
                Row(){
                    Image(
                        painter = painterResource(id = R.drawable.flood),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(horizontal = 5.dp)
                    )
                    Column(){
                        Text(text = address)
                        Text(text = emLevel)
                        Text(text = message)
                    }
                }
            }
        }
    }
}

@Composable
fun CriticalWeatherPhenomenonCardComponent(navController : NavController? = null, weatherPhenomenon: CriticalWeatherPhenomenon) {
    val imageResId = when(weatherPhenomenon) {
        CriticalWeatherPhenomenon.EARTHQUAKE -> R.drawable.earthquake
        CriticalWeatherPhenomenon.FLOOD -> R.drawable.flood
        CriticalWeatherPhenomenon.WILDFIRE -> R.drawable.wildfire
        CriticalWeatherPhenomenon.RIVER_FLOOD -> R.drawable.river_flood
        CriticalWeatherPhenomenon.HEATWAVE -> R.drawable.heatwave
        CriticalWeatherPhenomenon.SNOWSTORM -> R.drawable.snowstorm
        CriticalWeatherPhenomenon.STORM -> R.drawable.storm
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .clickable {
                       navController?.navigate("GroupEventsByLocation")
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Button(
            onClick = { /* Do something when button is clicked */ },
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(id = imageResId),
                    //painter = painterResource(id = R.drawable.storm),
                    contentDescription = "Button Image",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 8.dp) // Make the image fill the button
                )
                Text(
                    text = weatherPhenomenon.name,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SettingCard(screen: Screen.SettingsScreen, onClick: () -> Unit) {
    val color = if(screen.titleResId == R.string.logout) Color.Red else Color.Black

    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(6.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = color
                )
                Text(
                    text = stringResource(id = screen.titleResId),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 10.dp),
                    color = color
                )
            }
            if(screen.titleResId != R.string.logout) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow Icon"
                )
            }
        }
    }
}

@Composable
fun PermissionCard(
    iconResId: Int,
    permissionName: String,
    isExpanded: MutableState<Boolean>,
    switchState: MutableState<Boolean>,
    onToggleClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(8.dp), elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row (
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = iconResId),
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .size(40.dp),
                            contentDescription = null)
                        Text(
                            text = permissionName,
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Switch(
                        checked = switchState.value,
                        onCheckedChange = {
                            if (it) {
                                switchState.value = it
                                onToggleClick()
                            } else {
                                switchState.value = false
                            }
                        },
                        enabled = !switchState.value
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                IconButton(onClick = { isExpanded.value = !isExpanded.value }){
                    Icon(
                        painter = if (isExpanded.value) painterResource(id = R.drawable.arrow_up) else painterResource(id = R.drawable.arrow_down),
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        contentDescription = null
                    )
                }
            }
            if (isExpanded.value) {
                Row {
                    Text(
                        text = "Allow SmartAlert to access this device's $permissionName?",
                        modifier = Modifier.padding(start = 8.dp),
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}
