package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.theme.PrussianBlue

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if(isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Location permission is required to get your current location. Please go to app settings and enable the location permission."
        } else {
            "Location permission is required to get your current location. Please enable the location permission."
        }
    }
}

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Camera permission is required to take a picture. Please go to app settings and enable the camera permission."
        } else {
            "Camera permission is required to take a picture. Please enable the camera permission."
        }
    }
}

@Composable
fun AlertWithImageDialog(
    showDialog: Boolean,
    message: String?,
    imageURL: String?,
    onDismiss: () -> Unit
){
    if(showDialog){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(id = R.string.Citizen_message), color = PrussianBlue) },
            text = {
                Column(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = message ?: "")
                    // TODO: Text should be replaced with Image once the image is available
                    if (!imageURL.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageURL),
                            contentDescription = "Image",
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(15.dp))
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.No_image_available), color = Color.Red,
                        )
                    }
                }
            },
            confirmButton = {
                GeneralButtonComponent(
                    value = stringResource(id = R.string.close),
                    onButtonClicked = { onDismiss() }
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        )
    }

}

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm() }
                ) {
                    //Text("Confirm")
                    Text(text = stringResource(id = R.string.confirm), color = PrussianBlue)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    //Text("Cancel")
                    Text(text = stringResource(id = R.string.cancel), color = Color.Red)
                }
            }
        )
    }
}