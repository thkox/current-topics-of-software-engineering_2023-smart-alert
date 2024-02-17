package eu.tkacas.smartalert.ui.screen.intro

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import eu.tkacas.smartalert.R
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.PermissionCard
import eu.tkacas.smartalert.viewmodel.PermissionsViewModel


private lateinit var permissionLauncher: ActivityResultLauncher<String>
private var isLocationPermissionGranted = false
private var isRecordPermissionGranted = false

@Composable
fun PermissionsScreen(navController: NavController? = null) {

    val isExpandedLocation = remember { mutableStateOf(false) }
    val isExpandedCamera = remember { mutableStateOf(false) }

    val switchStateLocation = remember { mutableStateOf(false) }
    val switchStateCamera = remember { mutableStateOf(false) }

    val viewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = viewModel.permissions

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            granted ->
                viewModel.onPermissionResult(
                    permission = Manifest.permission.CAMERA,
                    granted = granted
                )
        }
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Permissions Screen", style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.size(20.dp))

        PermissionCard(
            iconResId = R.drawable.location_pin,
            permissionName = "Location",
            isExpanded = isExpandedLocation,
            switchState = switchStateLocation,
            onToggleClick = {

            })

        Spacer(modifier = Modifier.size(2.dp))

        PermissionCard(
            iconResId = R.drawable.photo_camera,
            permissionName = "Camera",
            isExpanded = isExpandedCamera,
            switchState = switchStateCamera,
            onToggleClick = {
                cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)

            })


        Spacer(modifier = Modifier.size(50.dp))
        GeneralButtonComponent(
            value = stringResource(id = R.string.next),
            onButtonClicked = {
                navController?.navigate("home")
            }
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    PermissionsScreen()
}