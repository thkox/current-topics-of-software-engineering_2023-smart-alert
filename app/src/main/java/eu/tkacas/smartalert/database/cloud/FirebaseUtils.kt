package eu.tkacas.smartalert.database.cloud

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.models.Bounds
import eu.tkacas.smartalert.models.CitizenMessage2
import eu.tkacas.smartalert.models.CriticalLevel
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import eu.tkacas.smartalert.models.LatLng
import eu.tkacas.smartalert.models.ListOfLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.ListOfSingleLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.LocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.LocationData
import eu.tkacas.smartalert.models.SingleLocationCriticalWeatherPhenomenonData
import kotlinx.coroutines.CompletableDeferred
import java.util.Locale

class FirebaseUtils {
    fun userExists(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun getUserID(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    fun getFirstName(onComplete: (Boolean, String?, String?) -> Unit) {

        val db = storageRef()
        val ref = db.getReference("users").child(getUserID()).child("firstName")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstName = dataSnapshot.getValue(String::class.java) ?: ""
                    onComplete(true, firstName, null)
                } else {
                    onComplete(false, null, "No first name found for user")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(false, null, "Error fetching data: ${databaseError.message}")
            }
        })
    }

    fun signOutUser() {
        FirebaseAuth.getInstance().signOut()
    }

    fun sendPasswordResetEmail(email: String, navController: NavController?) {
        FirebaseAuth
            .getInstance()
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        navController?.context,
                        navController?.context?.getString(R.string.password_reset_email_sent),
                        //"Password reset email sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signInUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }

    fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = getUserID()
                    val db = storageRef()
                    val userRef = db.getReference("users").child(uid)
                    val userData = mapOf("firstName" to firstName, "lastName" to lastName)
                    userRef.setValue(userData)
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }

    fun storageRef() = FirebaseDatabase.getInstance()


    fun getTranslatedPhenomenon(phenomenon: String): String {
        val currentLanguage = Locale.getDefault().language
        return when (phenomenon) {
            "EARTHQUAKE" -> if (currentLanguage == "el") "ΣΕΙΣΜΟΣ" else "EARTHQUAKE"
            "FLOOD" -> if (currentLanguage == "el") "ΠΛΗΜΜΥΡΑ" else "FLOOD"
            "WILDFIRE" -> if (currentLanguage == "el") "ΠΥΡΚΑΓΙΑ" else "WILDFIRE"
            "HEATWAVE" -> if (currentLanguage == "el") "ΚΑΥΣΩΝΑΣ" else "HEATWAVE"
            "SNOWSTORM" -> if (currentLanguage == "el") "ΧΙΟΝΟΘΥΕΛΛΑ" else "SNOWSTORM"
            "STORM" -> if (currentLanguage == "el") "ΚΑΤΑΙΓΙΔΑ" else "STORM"
            else -> phenomenon
        }
    }


    fun getAlertByPhenomenonAndLocation(
        phenomenon: String,
        onComplete: (Boolean, ListOfLocationCriticalWeatherPhenomenonData?, String?) -> Unit
    ) {
        val db = storageRef()
        val ref = db.getReference("alertsByPhenomenonAndLocationCountLast6h").child(phenomenon)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = ListOfLocationCriticalWeatherPhenomenonData(ArrayList())
                    for (snapshot in dataSnapshot.children) {
                        val locationID = snapshot.key ?: ""
                        val locationName = snapshot.child("name").getValue(String::class.java) ?: ""
                        val numOfReports = snapshot.child("counter").getValue(Int::class.java) ?: 0
                        data.list.add(
                            LocationCriticalWeatherPhenomenonData(
                                locationID,
                                locationName,
                                numOfReports
                            )
                        )
                    }
                    onComplete(true, data, null)
                } else {
                    //onComplete(false, null, "No alert found for $phenomenon")

                    val translatedPhenomenon = getTranslatedPhenomenon(phenomenon)
                    val currentLanguage = Locale.getDefault().language
                    when (currentLanguage) {
                        "en" -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }

                        "el" -> {
                            onComplete(false, null, "Δεν βρέθηκε ειδοποίηση για $translatedPhenomenon")
                        }

                        else -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //onComplete(false, null, "Error fetching data: ${databaseError.message}")

                val currentLanguage = Locale.getDefault().language
                when (currentLanguage) {
                    "en" -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }

                    "el" -> {
                        onComplete(false, null, "Σφάλμα ανάκτησης δεδομένων: ${databaseError.message}")
                    }

                    else -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }
                }
            }
        })
    }

    fun getAlertByPhenomenonAndLocationForMaps(
        phenomenon: String,
        onComplete: (Boolean, List<LocationData>?, String?) -> Unit
    ) {
        val db = storageRef()
        val ref = db.getReference("alertsByPhenomenonAndLocationLast6h").child(phenomenon)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = mutableListOf<LocationData>()
                    for (locationSnapshot in dataSnapshot.children) {
                        for (alertSnapshot in locationSnapshot.child("alertForms").children) {
                            val latitude = alertSnapshot.child("location").child("latitude")
                                .getValue(Double::class.java) ?: 0.0
                            val longitude = alertSnapshot.child("location").child("longitude")
                                .getValue(Double::class.java) ?: 0.0
                            val location = LocationData(latitude, longitude)
                            data.add(location)
                        }
                    }
                    onComplete(true, data, null)
                } else {
                    //onComplete(false, null, "No alert found for $phenomenon")

                    val translatedPhenomenon = getTranslatedPhenomenon(phenomenon)
                    val currentLanguage = Locale.getDefault().language
                    when (currentLanguage) {
                        "en" -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }

                        "el" -> {
                            onComplete(false, null, "Δεν βρέθηκε ειδοποίηση για $translatedPhenomenon")
                        }

                        else -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //onComplete(false, null, "Error fetching data: ${databaseError.message}")

                val currentLanguage = Locale.getDefault().language
                when (currentLanguage) {
                    "en" -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }

                    "el" -> {
                        onComplete(false, null, "Σφάλμα ανάκτησης δεδομένων: ${databaseError.message}")
                    }

                    else -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }
                }
            }
        })
    }

    fun getSpecificAlertByPhenomenonAndLocation(
        phenomenon: String,
        locationID: String,
        onComplete: (Boolean, ListOfSingleLocationCriticalWeatherPhenomenonData?, Bounds?, String?, String) -> Unit
    ) {
        val db = storageRef()
        val ref =
            db.getReference("alertsByPhenomenonAndLocationLast6h").child(phenomenon).child(locationID)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = ListOfSingleLocationCriticalWeatherPhenomenonData(ArrayList())
                    val boundsSnapshot = dataSnapshot.child("bounds")
                    val northeastLat =
                        boundsSnapshot.child("northeast").child("lat").getValue(Double::class.java)
                            ?: 0.0
                    val northeastLng =
                        boundsSnapshot.child("northeast").child("lng").getValue(Double::class.java)
                            ?: 0.0
                    val southwestLat =
                        boundsSnapshot.child("southwest").child("lat").getValue(Double::class.java)
                            ?: 0.0
                    val southwestLng =
                        boundsSnapshot.child("southwest").child("lng").getValue(Double::class.java)
                            ?: 0.0
                    val bounds =
                        Bounds(LatLng(northeastLat, northeastLng), LatLng(southwestLat, southwestLng))

                    val name = dataSnapshot.child("name").getValue(String::class.java) ?: ""

                    for (snapshot in dataSnapshot.child("alertForms").children) {
                        val alertID = snapshot.key ?: ""
                        val imageURL = snapshot.child("imageURL").getValue(String::class.java) ?: ""
                        val latitude =
                            snapshot.child("location").child("latitude").getValue(Double::class.java)
                                ?: ""
                        val longitude =
                            snapshot.child("location").child("longitude").getValue(Double::class.java)
                                ?: ""
                        val message = snapshot.child("message").getValue(String::class.java) ?: ""
                        val criticalLevelString =
                            snapshot.child("criticalLevel").getValue(String::class.java) ?: ""
                        val criticalLevel = CriticalLevel.valueOf(criticalLevelString)
                        val time = snapshot.child("time").getValue(String::class.java) ?: ""
                        val location = "$latitude, $longitude"
                        data.list.add(
                            SingleLocationCriticalWeatherPhenomenonData(
                                alertID,
                                location,
                                criticalLevel,
                                message,
                                imageURL,
                                time
                            )
                        )
                    }
                    onComplete(true, data, bounds, name, "Success")
                } else {
                    val translatedPhenomenon = getTranslatedPhenomenon(phenomenon)
                    val currentLanguage = Locale.getDefault().language
                    when (currentLanguage) {
                        "en" -> {
                            onComplete(
                                false,
                                null,
                                null,
                                null,
                                "No alert found for $translatedPhenomenon at $locationID"
                            )
                        }

                        "el" -> {
                            onComplete(
                                false,
                                null,
                                null,
                                null,
                                "Δεν βρέθηκε ειδοποίηση για $translatedPhenomenon στην τοποθεσία $locationID"
                            )
                        }

                        else -> {
                            onComplete(
                                false,
                                null,
                                null,
                                null,
                                "No alert found for $translatedPhenomenon at $locationID"
                            )
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val currentLanguage = Locale.getDefault().language
                when (currentLanguage) {
                    "en" -> {
                        onComplete(
                            false,
                            null,
                            null,
                            null,
                            "Error fetching data: ${databaseError.message}"
                        )
                    }

                    "el" -> {
                        onComplete(
                            false,
                            null,
                            null,
                            null,
                            "Σφάλμα ανάκτησης δεδομένων: ${databaseError.message}"
                        )
                    }

                    else -> {
                        onComplete(
                            false,
                            null,
                            null,
                            null,
                            "Error fetching data: ${databaseError.message}"
                        )
                    }
                }
            }
        })
    }

    fun getSpecificAlertByPhenomenonAndLocationForMaps(
        phenomenon: String,
        locationID: String,
        onComplete: (Boolean, List<LocationData>?, String?) -> Unit
    ) {
        val db = storageRef()
        val ref =
            db.getReference("alertsByPhenomenonAndLocationLast6h").child(phenomenon).child(locationID)
                .child("alertForms")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = mutableListOf<LocationData>()
                    for (snapshot in dataSnapshot.children) {
                        val latitude =
                            snapshot.child("location").child("latitude").getValue(Double::class.java)
                                ?: 0.0
                        val longitude =
                            snapshot.child("location").child("longitude").getValue(Double::class.java)
                                ?: 0.0
                        val location = LocationData(latitude, longitude)
                        data.add(location)
                    }
                    onComplete(true, data, null)
                } else {
                    //onComplete(false, null, "No alert found for $phenomenon at $location")

                    val translatedPhenomenon = getTranslatedPhenomenon(phenomenon)
                    val currentLanguage = Locale.getDefault().language
                    when (currentLanguage) {
                        "en" -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }

                        "el" -> {
                            onComplete(false, null, "Δεν βρέθηκε ειδοποίηση για $translatedPhenomenon")
                        }

                        else -> {
                            onComplete(false, null, "No alert found for $translatedPhenomenon")
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //onComplete(false, null, "Error fetching data: ${databaseError.message}")

                val currentLanguage = Locale.getDefault().language
                when (currentLanguage) {
                    "en" -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }

                    "el" -> {
                        onComplete(false, null, "Σφάλμα ανάκτησης δεδομένων: ${databaseError.message}")
                    }

                    else -> {
                        onComplete(false, null, "Error fetching data: ${databaseError.message}")
                    }
                }
            }
        })
    }

    fun getAlertFormsByUser(onComplete: (Boolean, List<CitizenMessage2>?, String?) -> Unit) {
        val db = storageRef()
        val ref = db.getReference("alertForms").child(getUserID())

        // Get all the citizenMessages
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = mutableListOf<CitizenMessage2>()
                    for (snapshot in dataSnapshot.children) {
                        // Create a citizen message object from the snapshot
                        val message = snapshot.child("message").getValue(String::class.java) ?: ""
                        val phenomenonString =
                            snapshot.child("criticalWeatherPhenomenon").getValue(String::class.java)
                                ?: ""
                        val phenomenon = CriticalWeatherPhenomenon.valueOf(phenomenonString)
                        val criticalLevelString =
                            snapshot.child("criticalLevel").getValue(String::class.java) ?: ""
                        val criticalLevel = CriticalLevel.valueOf(criticalLevelString)
                        val latitude =
                            snapshot.child("location").child("latitude").getValue(Double::class.java)
                                ?: 0.0
                        val longitude =
                            snapshot.child("location").child("longitude").getValue(Double::class.java)
                                ?: 0.0
                        val location = LocationData(latitude, longitude)
                        // Convert timestamp to string "year-month-day hour:minute"
                        val tempTimestamp = snapshot.child("timestamp").getValue(Long::class.java) ?: 0
                        val convertedTimestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
                            .format(java.util.Date(tempTimestamp))
                        val imageURL = snapshot.child("imageURL").getValue(String::class.java) ?: ""

                        val citizenMessage2 = CitizenMessage2(
                            message,
                            phenomenon,
                            criticalLevel,
                            location,
                            convertedTimestamp,
                            imageURL
                        )
                        data.add(citizenMessage2)
                    }
                    onComplete(true, data, null)
                } else {
                    onComplete(false, null, "No alert forms found for user")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(false, null, "Error fetching data: ${databaseError.message}")
            }
        })
    }

    fun getStatisticsPerYear(onComplete: (Boolean, Map<String, Any>?, String?) -> Unit) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("statisticsPerYear")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = dataSnapshot.value as Map<String, Any>
                    onComplete(true, data, null)
                } else {
                    onComplete(false, null, "No statistics found for year")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(false, null, "Error fetching data: ${databaseError.message}")
            }
        })
    }

    fun saveToken(token: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("tokens")
        ref.push().setValue(token)
    }

    suspend fun changeUserPassword(newPassword: String): Boolean {
        val user = Firebase.auth.currentUser
        val result = CompletableDeferred<Boolean>()

        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AccountViewModel", "User password updated.")
                result.complete(true)
            } else {
                Log.e("AccountViewModel", "Failed to update user password.", task.exception)
                result.complete(false)
            }
        }

        return result.await()
    }
}