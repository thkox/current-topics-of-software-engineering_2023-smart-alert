package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon

@Composable
fun <T : Enum<T>> EnumDropdownComponent(
    enumClass: Class<T>,
    initialSelection: T,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val enumValues = enumClass.enumConstants
    var selectedValue by remember { mutableStateOf(initialSelection) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorBlue)),
        ) {
            Text(selectedValue.name)
            Icon(painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(id = R.drawable.arrow_down),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(colorResource(id = R.color.colorBlue))
        ) {
            enumValues.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(colorResource(id = R.color.colorBlue))
                ) {
                    Text(value.name)
                }
            }
        }
    }
}