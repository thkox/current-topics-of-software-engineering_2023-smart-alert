package eu.tkacas.smartalert.cloud

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CloudFunctionsUtils {

    private var functions: FirebaseFunctions = Firebase.functions

    suspend fun userIsEmployee(): Boolean {
        return try {
            val result = functions
                .getHttpsCallable("userIsEmployee")
                .call()
                .continueWith { task ->
                    val result = task.result?.data as Map<String, Any>
                    result["isCP"] as Boolean
                }.await()
            result
        } catch (e: Exception) {
            // Handle exception here, you might want to return false or rethrow the exception
            false
        }
    }
}