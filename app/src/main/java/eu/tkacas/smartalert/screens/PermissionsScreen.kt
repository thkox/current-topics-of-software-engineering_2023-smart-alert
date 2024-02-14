package eu.tkacas.smartalert.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import eu.tkacas.smartalert.R
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
    val isExpanded = remember { mutableStateOf(false) }

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
                                painter = painterResource(id = R.drawable.location_pin),
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .size(40.dp),
                                contentDescription = null)
                            Text(
                                text = "Location",
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
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it }
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
                if (isExpanded.value) { // Add this line
                    Row (
                    ) {
                        Text(
                            text = "Allow SmartAlert to access this device's location?",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }
            }
        }
    }
}