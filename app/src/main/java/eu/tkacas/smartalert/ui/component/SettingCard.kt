package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tkacas.smartalert.ui.screen.Screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.Alignment


@Composable
fun SettingCard(screen: Screen.SettingsScreen, onClick: () -> Unit) {
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
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = screen.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow Icon"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingCardPreview(){
    SettingCard(screen = Screen.SettingsScreen.Account, onClick = {})
}
