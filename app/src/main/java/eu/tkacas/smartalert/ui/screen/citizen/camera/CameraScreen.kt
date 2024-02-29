package eu.tkacas.smartalert.ui.screen.citizen.camera

import android.util.Log
import android.widget.Toast
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import eu.tkacas.smartalert.ui.component.CameraPreview
import eu.tkacas.smartalert.ui.component.PhotoBottomSheetContent
import eu.tkacas.smartalert.ui.theme.BlueColor
import eu.tkacas.smartalert.ui.theme.BlueGreen
import eu.tkacas.smartalert.viewmodel.citizen.CameraViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavHostController? = null){
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    val viewModel = viewModel<CameraViewModel>()
    val bitmaps by viewModel.bitmaps.collectAsState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            PhotoBottomSheetContent(
                bitmaps = bitmaps,
                modifier = Modifier
                    .fillMaxWidth(),
                onPhotoSelected = { bitmap ->
                    scope.launch {
                        val citizenMessage = viewModel.getCitizenMessageFromPrefs(context)
                        citizenMessage?.imageURL = viewModel.uploadPhotoToCloudStorage(bitmap = bitmap)
                        if (citizenMessage?.imageURL != "") {
                            viewModel.saveCitizenMessageToPrefs(context, citizenMessage)
                            val currentLanguage = Locale.getDefault().language
                            val toastCameraTrueMessage = when (currentLanguage) {
                                "en" -> "Image uploaded successfully"
                                "el" -> "Η εικόνα μεταφορτώθηκε με επιτυχία"
                                else -> "Image uploaded successfully"
                            }
                            Toast.makeText(context, toastCameraTrueMessage, Toast.LENGTH_SHORT).show()
                            //Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                            navController?.navigate("alertForm")
                        } else {
                            Log.d("CameraScreen", "Image URL is empty")
                            val currentLanguage = Locale.getDefault().language
                            val toastCameraFalseMessage = when (currentLanguage) {
                                "en" -> "The image did not upload correctly, please try again later"
                                "el" -> "Η εικόνα δεν μεταφορτώθηκε σωστά, δοκιμάστε ξανά αργότερα"
                                else -> "The image did not upload correctly, please try again later"
                            }
                            Toast.makeText(context, toastCameraFalseMessage, Toast.LENGTH_SHORT).show()
                            //Toast.makeText(context, "The image did not upload correctly, please try again later", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = BlueGreen,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = "Open gallery"
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = BlueGreen,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    onClick = {
                        viewModel.takePhoto(
                            controller = controller,
                            onPhotoTaken = viewModel::onTakePhoto,
                            context = context
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Take photo"
                    )
                }
                Box(
                    Modifier
                        .size(48.dp)
                )
            }
        }
    }
}

