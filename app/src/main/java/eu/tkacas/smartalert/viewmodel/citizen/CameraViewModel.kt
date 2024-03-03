package eu.tkacas.smartalert.viewmodel.citizen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import eu.tkacas.smartalert.models.CitizenMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.Locale
import java.util.UUID

class CameraViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap) {
        _bitmaps.value += bitmap
    }

    suspend fun handlePhotoUpload(bitmap: Bitmap, context: Context): Pair<Boolean, String> {
        val citizenMessage = getCitizenMessageFromPrefs(context)
        citizenMessage?.imageURL = uploadPhotoToCloudStorage(bitmap = bitmap)
        return if (citizenMessage?.imageURL != "") {
            saveCitizenMessageToPrefs(context, citizenMessage)
            val currentLanguage = Locale.getDefault().language
            val toastCameraTrueMessage = when (currentLanguage) {
                "en" -> "Image uploaded successfully"
                "el" -> "Η εικόνα μεταφορτώθηκε με επιτυχία"
                else -> "Image uploaded successfully"
            }
            Pair(true, toastCameraTrueMessage)
        } else {
            Log.d("CameraScreen", "Image URL is empty")
            val currentLanguage = Locale.getDefault().language
            val toastCameraFalseMessage = when (currentLanguage) {
                "en" -> "The image did not upload correctly, please try again later"
                "el" -> "Η εικόνα δεν μεταφορτώθηκε σωστά, δοκιμάστε ξανά αργότερα"
                else -> "The image did not upload correctly, please try again later"
            }
            Pair(false, toastCameraFalseMessage)
        }
    }

    fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit,
        context: Context
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply{
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }

                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    onPhotoTaken(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ", exception)
                }
            }
        )
    }
    private suspend fun uploadPhotoToCloudStorage(bitmap: Bitmap): String {
        // Convert the Bitmap to a byte array
        val boas = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas)
        val data = boas.toByteArray()

        // Get a reference to Firebase Storage
        val storage = FirebaseStorage.getInstance()

        // Create a unique file name
        val fileName = UUID.randomUUID().toString()

        // Create a reference to the location where you want to upload the file
        val storageRef = storage.reference.child("images/$fileName.jpg")

        // Upload the byte array to Firebase Storage
        val uploadTask = storageRef.putBytes(data)

        // After the upload, get the download URL of the uploaded photo
        return uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.await().toString()
    }

    private fun citizenMessageToJson(citizenMessage: CitizenMessage): String {
        return Gson().toJson(citizenMessage)
    }

    private fun jsonToCitizenMessage(jsonString: String): CitizenMessage {
        return Gson().fromJson(jsonString, CitizenMessage::class.java)
    }

    private fun saveCitizenMessageToPrefs(context: Context, citizenMessage: CitizenMessage? = null) {
        val jsonString = citizenMessage?.let { citizenMessageToJson(it) }
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("citizenMessage", jsonString).apply()
    }

    private fun getCitizenMessageFromPrefs(context: Context): CitizenMessage? {
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jsonString = prefs.getString("citizenMessage", null)
        return if (jsonString != null) {
            jsonToCitizenMessage(jsonString)
        } else {
            null
        }
    }


}