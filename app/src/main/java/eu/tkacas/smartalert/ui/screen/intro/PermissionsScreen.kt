package eu.tkacas.smartalert.ui.screen.intro

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import eu.tkacas.smartalert.R
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.ui.component.CameraPermissionTextProvider
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.LocationPermissionTextProvider
import eu.tkacas.smartalert.ui.component.PermissionCard
import eu.tkacas.smartalert.ui.component.PermissionDialog
import eu.tkacas.smartalert.viewmodel.PermissionsViewModel


@Composable
fun PermissionsScreen(navController: NavController? = null) {

    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA,
    )

    val isExpandedLocation = remember { mutableStateOf(false) }
    val isExpandedCamera = remember { mutableStateOf(false) }

    val switchStateCoarseLocation = remember { mutableStateOf(false) }
    val switchStateCamera = remember { mutableStateOf(false) }

    val viewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )
        }
    )

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                isGranted = isGranted
            )
        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                when (permission) {
                    Manifest.permission.CAMERA -> switchStateCamera.value = perms[permission] == true
                    Manifest.permission.ACCESS_COARSE_LOCATION -> switchStateCoarseLocation.value = perms[permission] == true
                }
            }
        }
    )

    // Check the permission status when the composable becomes active
    LaunchedEffect(Unit) {
        switchStateCamera.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        switchStateCoarseLocation.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


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
            switchState = switchStateCoarseLocation,
            //isEnabled = !switchStateCoarseLocation.value,
            onToggleClick = {
                if (switchStateCoarseLocation.value) {
                    locationPermissionResultLauncher.launch(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                }
            })

        Spacer(modifier = Modifier.size(2.dp))

        PermissionCard(
            iconResId = R.drawable.photo_camera,
            permissionName = "Camera",
            isExpanded = isExpandedCamera,
            switchState = switchStateCamera,
            //isEnabled = !switchStateCamera.value,
            onToggleClick = {
                if (switchStateCamera.value) {
                    cameraPermissionResultLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        )

        


        Spacer(modifier = Modifier.size(50.dp))
        GeneralButtonComponent(
            value = stringResource(id = R.string.next),
            onButtonClicked = {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
                if (areAllPermissionsGranted(context, permissionsToRequest)) {
                    navController?.navigate("home")
                } else {
                    Toast.makeText(context, "Please grant all permissions", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    context as Activity,
                    permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = { openAppSettings(context) }
            )
        }
}

fun openAppSettings(context: Context) {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    ).also { intent ->
        context.startActivity(intent)
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    PermissionsScreen()
}

fun areAllPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
    return permissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

