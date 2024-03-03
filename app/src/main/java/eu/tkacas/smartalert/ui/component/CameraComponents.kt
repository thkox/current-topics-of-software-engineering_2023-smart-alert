package eu.tkacas.smartalert.ui.component

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.theme.BlueColor
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

@Composable
fun PhotoBottomSheetContent(
    bitmaps: List<Bitmap>,
    modifier: Modifier = Modifier,
    onPhotoSelected: (Bitmap) -> Unit
) {
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val scope = rememberCoroutineScope()

    if(bitmaps.isEmpty()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Text(stringResource(id = R.string.there_are_no_photos_yet))
        }
    } else {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Text(stringResource(id = R.string.please_choose_a_photo_to_upload))
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(bitmaps) { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .let { if (bitmap == selectedBitmap) it.border(5.dp, BlueColor) else it }
                        .clickable { selectedBitmap = bitmap }
                )
            }
        }
        if (selectedBitmap != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ){
                UploadPhotoButton(
                    onButtonClicked = {
                        scope.launch {
                            onPhotoSelected(selectedBitmap!!)
                        }
                    }
                )
            }
        }
    }
}