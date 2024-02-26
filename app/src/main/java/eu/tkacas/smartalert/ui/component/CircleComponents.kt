package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun CircleImage(imageResId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .size(100.dp)
    )
}