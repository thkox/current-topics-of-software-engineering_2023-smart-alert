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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalLevelDropdown
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.Months
import eu.tkacas.smartalert.models.SortingCriteriaDropDown
import eu.tkacas.smartalert.ui.theme.BlueGreen


@Composable
fun EnumDropdownComponent(
    enumClass: Class<CriticalWeatherPhenomenon>,
    initialSelection: CriticalWeatherPhenomenon,
    onSelected: (CriticalWeatherPhenomenon) -> Unit,
    enabled: Boolean = true
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialSelection) }


    Box {
        Button(
            onClick = { if (enabled) expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueGreen),
        ) {
            Text(stringResource(id = selectedValue.getStringId()))
            if (enabled) {
                Icon(
                    painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(
                        id = R.drawable.arrow_down
                    ),
                    contentDescription = if (expanded) "Arrow Up" else "Arrow Down"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BlueGreen),
        ) {
            enumClass.enumConstants?.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(BlueGreen)
                ) {
                    Text(text = stringResource(id = value.getStringId()))
                }
            }
        }
    }
}

@Composable
fun YearDropdownComponent(
    years: List<String>?,
    initialSelection: String?,
    onSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialSelection) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueGreen),
        ) {
            Text(selectedValue ?: "Default Value")
            Icon(
                painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(
                    id = R.drawable.arrow_down
                ),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BlueGreen),
        ) {
            years?.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(BlueGreen)
                ) {
                    Text(text = value)
                }
            }
        }
    }
}

@Composable
fun EnumDropdownComponentMonths(
    enumClass: Class<Months>,
    initialSelection: Months,
    onSelected: (Months) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialSelection) }


    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueGreen),
        ) {
            Text(stringResource(id = selectedValue.getStringId()))
            Icon(
                painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(
                    id = R.drawable.arrow_down
                ),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BlueGreen),
        ) {
            enumClass.enumConstants?.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(BlueGreen)
                ) {
                    Text(text = stringResource(id = value.getStringId()))
                }
            }
        }
    }
}

@Composable
fun EnumDropdownComponentCriticalLevelAlert(
    enumClass: Class<CriticalLevelDropdown>,
    initialSelection: CriticalLevelDropdown,
    onSelected: (CriticalLevelDropdown) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialSelection) }


    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueGreen),
        ) {
            Text(stringResource(id = selectedValue.getStringId()))
            Icon(
                painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(
                    id = R.drawable.arrow_down
                ),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BlueGreen),
        ) {
            enumClass.enumConstants?.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(BlueGreen)
                ) {
                    Text(text = stringResource(id = value.getStringId()))
                }
            }
        }
    }
}

@Composable
fun EnumDropdownComponentSortingCriteria(
    enumClass: Class<SortingCriteriaDropDown>,
    initialSelection: SortingCriteriaDropDown,
    onSelected: (SortingCriteriaDropDown) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialSelection) }


    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueGreen),
        ) {
            Text(stringResource(id = selectedValue.getStringId()))
            Icon(
                painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(
                    id = R.drawable.arrow_down
                ),
                contentDescription = if (expanded) "Arrow Up" else "Arrow Down"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BlueGreen),
        ) {
            enumClass.enumConstants?.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = value
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier.background(BlueGreen)
                ) {
                    Text(text = stringResource(id = value.getStringId()))
                }
            }
        }
    }
}



