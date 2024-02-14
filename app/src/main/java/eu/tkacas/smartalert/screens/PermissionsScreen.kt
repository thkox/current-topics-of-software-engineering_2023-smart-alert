package eu.tkacas.smartalert.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import eu.tkacas.smartalert.R
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PermissionsScreen() {
    val checkedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Permissions Screen")
        Card(
            modifier = Modifier.padding(8.dp), elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row (
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_pin),
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .size(40.dp),
                        contentDescription = null)
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Location",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Text(
                            text = "Allow SmartAlert to access this device's location?",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it }
                    )
                }
            }
        }
    }
}