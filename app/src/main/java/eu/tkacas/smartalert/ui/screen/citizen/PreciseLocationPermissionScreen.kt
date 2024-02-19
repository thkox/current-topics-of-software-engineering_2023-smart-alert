package eu.tkacas.smartalert.ui.screen.citizen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.permissions.areAllPermissionsGranted
import eu.tkacas.smartalert.permissions.openAppSettings
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.ui.component.LocationPermissionTextProvider
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.component.PermissionDialog
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.PreciseLocationViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PreciseLocationPermissionScreen(navController: NavController? = null){
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val viewModel = viewModel<PreciseLocationViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGranted = isGranted
            )
            if (isGranted && areAllPermissionsGranted(context, permissionsToRequest)) {
                navController?.navigate("alertForm")
            }
        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(title = "Precise Location Permission", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            HeadingTextComponent(value = "We need your precise location to alert other citizens in your area.")
            Spacer(modifier = Modifier.size(35.dp))
            Image(
                painter = painterResource(id = R.drawable.location_map),
                contentDescription = "Precise Location Permission Map",
                modifier = Modifier
                    .size(250.dp)
            )
            Spacer(modifier = Modifier.size(30.dp))
            NormalTextComponent(value = "Please enable precise location permission to continue.")
            Spacer(modifier = Modifier.size(25.dp))
            GeneralButtonComponent(
                value = stringResource(id = R.string.allow),
                onButtonClicked = {
                    locationPermissionResultLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
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
                        else -> return@forEach
                    },
                    isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
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
}

@Preview
@Composable
fun PreciseLocationPermissionScreenPreview(){
    PreciseLocationPermissionScreen()
}
