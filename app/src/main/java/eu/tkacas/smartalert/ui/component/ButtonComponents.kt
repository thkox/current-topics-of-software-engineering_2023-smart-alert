package eu.tkacas.smartalert.ui.component

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.ui.theme.BlueColor
import eu.tkacas.smartalert.ui.theme.OrangeColor
import eu.tkacas.smartalert.ui.theme.Primary
import eu.tkacas.smartalert.ui.theme.Secondary
import eu.tkacas.smartalert.ui.theme.YellowColor

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
fun CriticalWeatherPhenomenonButtonComponent(imageResId: Int, weatherPhenomenon: CriticalWeatherPhenomenon) {
    val imageResId = when(weatherPhenomenon) {
        CriticalWeatherPhenomenon.EARTHQUAKE -> R.drawable.earthquake
        CriticalWeatherPhenomenon.FLOOD -> R.drawable.flood
        CriticalWeatherPhenomenon.WILDFIRE -> R.drawable.wildfire
        CriticalWeatherPhenomenon.RIVER_FLOOD -> R.drawable.river_flood
        CriticalWeatherPhenomenon.HEATWAVE -> R.drawable.heatwave
        CriticalWeatherPhenomenon.SNOWSTORM -> R.drawable.snowstorm
        CriticalWeatherPhenomenon.STORM -> R.drawable.storm
    }
    Box(
        modifier = Modifier
            .size(100.dp)
            //Add a 3D Effect to the button
            //.shadow(20.dp, RoundedCornerShape(20.dp))
    ) {
        Button(
            onClick = { /* Do something when button is clicked */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White),
            shape = RoundedCornerShape(20.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                //painter = painterResource(id = R.drawable.storm),
                contentDescription = "Button Image",
                modifier = Modifier.fillMaxSize() // Make the image fill the button
            )
            Text(
                text = weatherPhenomenon.name,
                color = Color.Black
            )
        }
    }
}


@Composable
fun AlertLevelButtonsRowComponent() {
    var selectedDangerLevelButton by remember { mutableStateOf(1) }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
        ){
        Button(
            onClick = { selectedDangerLevelButton = 1 },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDangerLevelButton == 1) YellowColor else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let { if (selectedDangerLevelButton == 1) it.border(2.dp, Color.Black, RoundedCornerShape(10.dp)) else it }
        ){
            Text(
                text = "Low",
                color = if (selectedDangerLevelButton == 1) Color.Black else Color.White
            )
        }
        Button(
            onClick = { selectedDangerLevelButton = 2 },
            enabled = false,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDangerLevelButton == 2) OrangeColor else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let { if (selectedDangerLevelButton == 2) it.border(2.dp, Color.Black, RoundedCornerShape(10.dp)) else it }
        ){
            Text(
                text = "Medium",
                color = if (selectedDangerLevelButton == 2) Color.Black else Color.White
                )
        }
        Button(
            onClick = { selectedDangerLevelButton = 3 },
            enabled = false,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDangerLevelButton == 3) Color.Red else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let { if (selectedDangerLevelButton == 3) it.border(2.dp, Color.Black, RoundedCornerShape(10.dp)) else it }
        ){
            Text(
                text = "High",
                color = if (selectedDangerLevelButton == 3) Color.Black else Color.White
            )
        }
    }
}

@Composable
fun GeneralButtonComponent(value: String, onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .width(175.dp)
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(40.dp)
                .background(
                    color = BlueColor,
                    shape = RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                //text = "High",
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}