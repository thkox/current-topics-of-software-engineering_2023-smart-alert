package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.EmergencyLevel

@Preview
@Composable
fun LevelDropdownComponent() {
    var expanded by remember { mutableStateOf(false) }
    val levels = EmergencyLevel.entries.toTypedArray()
    var selectedLevel by remember { mutableStateOf(levels[0]) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedLevel.name)
            Icon(painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(id = R.drawable.arrow_down),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down",
                tint = Color.White)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            levels.forEach { level ->
                DropdownMenuItem(onClick = {
                    selectedLevel = level
                    expanded = false
                }) {
                    Text(level.name)
                }
            }
        }
    }
}