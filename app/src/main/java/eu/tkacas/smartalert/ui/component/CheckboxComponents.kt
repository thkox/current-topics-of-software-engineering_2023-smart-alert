package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.SkyBlue

@Composable
fun CheckboxComponent(
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center

    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange.invoke(it)
            },
            colors = CheckboxDefaults.colors(
                checkmarkColor = PrussianBlue,
                checkedColor = SkyBlue
            ),
        )
    }

}

