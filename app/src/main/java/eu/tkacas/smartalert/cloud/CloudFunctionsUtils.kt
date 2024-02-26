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
                .getHttpsCallable("user_is_employee")
                .call()
                .continueWith { task ->
                    val result = task.result?.data as Map<String, Any>
                    result["isCP"] as Boolean
                }.await()
            result
        } catch (e: Exception) {
            // TODO: Handle exception here, you might want to return false or rethrow the exception
            false
        }
    }

    suspend fun deleteAlertsByPhenomenonAndLocation(phenomenon: String, location: String): Boolean {
        val data = hashMapOf(
            "phenomenon" to phenomenon,  // Example phenomenon
            "place" to location // Example place
        )

        return try {
            val result = functions
                .getHttpsCallable("delete_alerts_by_location")
                .call(data)
                .continueWith { task ->
                    val result = task.result?.data as Map<String, Any>
                    result["success"] as Boolean
                }.await()
            result
        } catch (e: Exception) {
            println("Error calling Firebase Function: ${e.message}")
            false
        }
    }

    suspend fun deleteAlertByPhenomenonAndLocation(phenomenon: String, location: String, alertID: String): Boolean {
    val data = hashMapOf(
        "phenomenon" to phenomenon,
        "place" to location,
        "alertID" to alertID
    )

    return try {
        val result = functions
            .getHttpsCallable("delete_alert_by_phenomenon_and_location")
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as Map<String, Any>
                result["success"] as Boolean
            }.await()
        result
    } catch (e: Exception) {
        println("Error calling Firebase Function: ${e.message}")
        false
    }
}
}