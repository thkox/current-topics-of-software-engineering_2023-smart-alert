package eu.tkacas.smartalert.ui.screen.intro

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import eu.tkacas.smartalert.R
import androidx.compose.material3.Text
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
import eu.tkacas.smartalert.permissions.areAllPermissionsGranted
import eu.tkacas.smartalert.permissions.openAppSettings
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.LocationPermissionTextProvider
import eu.tkacas.smartalert.ui.component.PermissionCard
import eu.tkacas.smartalert.ui.component.PermissionDialog
import eu.tkacas.smartalert.ui.component.REDUnderLinedTextComponent
import eu.tkacas.smartalert.viewmodel.intro.PermissionsViewModel
import java.util.Locale


@Composable
fun PermissionsScreen(navController: NavController? = null) {

    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    )

    val isExpandedLocation = remember { mutableStateOf(false) }

    val switchStateCoarseLocation = remember { mutableStateOf(false) }
    val switchStateBackgroundLocation = remember { mutableStateOf(false) }

    val viewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue


    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isGranted
            )
            switchStateCoarseLocation.value = isGranted
        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> switchStateCoarseLocation.value = perms[permission] == true
                }
            }
        }
    )

    // Check the permission status when the composable becomes active
    LaunchedEffect(Unit) {
        switchStateBackgroundLocation.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        switchStateCoarseLocation.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = stringResource(id = R.string.Permissions), style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.size(20.dp))



        PermissionCard(
            iconResId = R.drawable.location_pin,
            permissionName = stringResource(id = R.string.Location),
            isExpanded = isExpandedLocation,
            switchState = switchStateCoarseLocation,
            onToggleClick = {
                if (switchStateCoarseLocation.value) {
                    locationPermissionResultLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            })

        Spacer(modifier = Modifier.size(15.dp))

        REDUnderLinedTextComponent(
            value = stringResource(id = R.string.Always_allow_location_permission),
            onClick = {openAppSettings(context)}
        )

        

        Spacer(modifier = Modifier.size(50.dp))
        GeneralButtonComponent(
            value = stringResource(id = R.string.next),
            onButtonClicked = {
                if (areAllPermissionsGranted(context, permissionsToRequest)) {
                    navController?.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                } else {
                    val currentLanguage = Locale.getDefault().language
                    val toastPermissionMessage = when (currentLanguage) {
                        "en" -> "Please grant all permissions"
                        "el" -> "Παρακαλώ επιτρέψτε όλα τα δικαιώματα"
                        else -> "Please grant all permissions"
                    }
                    Toast.makeText(context, toastPermissionMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    context as Activity,
                    permission
                ),
                onDismiss = {
                    viewModel.dismissDialog()
                },
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

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    PermissionsScreen()
}
