package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import eu.tkacas.smartalert.R



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
fun cardComponentWithImage() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp){
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
                        Text(text = "Address")
                        Text(text = "Emergency level")
                        Text(text = "Tap to show the message")
                    }
                }
            }
            Column(){

            }
        }
    }
}