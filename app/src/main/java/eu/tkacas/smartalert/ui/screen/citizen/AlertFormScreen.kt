package eu.tkacas.smartalert.ui.screen.citizen

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.permissions.openAppSettings
import eu.tkacas.smartalert.ui.component.AlertLevelButtonsRowComponent
import eu.tkacas.smartalert.ui.component.CameraButton
import eu.tkacas.smartalert.ui.component.CameraPermissionTextProvider
import eu.tkacas.smartalert.ui.component.EnumDropdownComponent
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.MultilineTextFieldComponent
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.component.PermissionDialog
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.viewmodel.citizen.AlertFormViewModel

@Composable
fun AlertFormScreen(navController: NavHostController? = null) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA
    )

    val viewModel = viewModel<AlertFormViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )
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
            AppBarBackView(title = stringResource(id = R.string.create_a_new_alert), navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.size(80.dp))
                NormalTextComponent(value = stringResource(id = R.string.emergency_level))
                AlertLevelButtonsRowComponent()
                Spacer(modifier = Modifier.size(16.dp))
                NormalTextComponent(value = stringResource(id = R.string.weather_phenomenon_selection))
                EnumDropdownComponent(CriticalWeatherPhenomenon::class.java)
                Spacer(modifier = Modifier.size(16.dp))
                NormalTextComponent(value = stringResource(id = R.string.writeADescription))
                MultilineTextFieldComponent()
                Spacer(modifier = Modifier.size(16.dp))
                NormalTextComponent(value = stringResource(id = R.string.takeAPicture))
                Spacer(modifier = Modifier.size(8.dp))
                CameraButton(
                    onButtonClicked = {
                        locationPermissionResultLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                        navController?.navigate("camera")
                    }
                )
                Spacer(modifier = Modifier.size(25.dp))
                GeneralButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onButtonClicked = {
                        //TODO Add the logic to submit the alert
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
}

@Preview
@Composable
fun AlertFormScreenPreview(){
    AlertFormScreen()
}