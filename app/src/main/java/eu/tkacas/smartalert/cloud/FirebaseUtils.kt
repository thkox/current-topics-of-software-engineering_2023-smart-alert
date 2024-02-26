package eu.tkacas.smartalert.cloud

import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.tkacas.smartalert.models.ListOfLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.ListOfSingleLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.LocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.LocationData
import eu.tkacas.smartalert.models.SingleLocationCriticalWeatherPhenomenonData

fun userExists() : Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}

fun getUserID() : String {
    return FirebaseAuth.getInstance().currentUser?.uid ?: ""
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
                    "Password reset email sent successfully",
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

fun createUser(email: String, password: String, firstName: String, lastName: String, onComplete: (Boolean, String?) -> Unit) {
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

fun getAlertByPhenomenonAndLocation(phenomenon: String, onComplete: (Boolean, ListOfLocationCriticalWeatherPhenomenonData?, String?) -> Unit) {
    val db = storageRef()
    val ref = db.getReference("alertsByPhenomenonAndLocationCountLast24h").child(phenomenon) // Assuming a structure like 'alerts/<phenomenon>'

    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val data = ListOfLocationCriticalWeatherPhenomenonData(ArrayList())
                for (snapshot in dataSnapshot.children) {
                    val location = snapshot.key ?: continue
                    val numOfReports = snapshot.getValue(Int::class.java) ?: continue
                    data.list.add(LocationCriticalWeatherPhenomenonData(location, numOfReports))
                }
                onComplete(true, data, null)
            } else {
                onComplete(false, null, "No alert found for $phenomenon")
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onComplete(false, null, "Error fetching data: ${databaseError.message}")
        }
    })
}

fun getAlertByPhenomenonAndLocationForMaps(phenomenon: String, onComplete: (Boolean, List<LocationData>?, String?) -> Unit) {
    val db = storageRef()
    val ref = db.getReference("alertsByPhenomenonAndLocationLast24h").child(phenomenon)

    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val data = mutableListOf<LocationData>()
                for (locationSnapshot in dataSnapshot.children) {
                    for  (alertSnapshot in locationSnapshot.children) {
                        val latitude = alertSnapshot.child("location").child("latitude").getValue(Double::class.java)?:0.0
                        val longitude = alertSnapshot.child("location").child("longitude").getValue(Double::class.java)?:0.0
                        val location = LocationData(latitude, longitude)
                        data.add(location)
                    }
                }
                onComplete(true, data, null)
            } else {
                onComplete(false, null, "No alert found for $phenomenon")
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onComplete(false, null, "Error fetching data: ${databaseError.message}")
        }
    })
}

fun getSpecificAlertByPhenomenonAndLocation(phenomenon: String, location: String, onComplete: (Boolean, ListOfSingleLocationCriticalWeatherPhenomenonData?, String) -> Unit) {
    val db = storageRef()
    val ref = db.getReference("alertsByPhenomenonAndLocationLast24h").child(phenomenon).child(location)

    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val data = ListOfSingleLocationCriticalWeatherPhenomenonData(ArrayList())
                for (snapshot in dataSnapshot.children) {
                    val alertID = snapshot.key?:""
                    val imageURL = snapshot.child("imageURL").getValue(String::class.java)?:""
                    val latitude = snapshot.child("location").child("latitude").getValue(Double::class.java)?:""
                    val longitude = snapshot.child("location").child("longitude").getValue(Double::class.java)?:""
                    val message = snapshot.child("message").getValue(String::class.java)?:""
                    val emLevel = snapshot.child("criticalLevel").getValue(Int::class.java)?:0
                    val timeStamp = snapshot.child("timestamp").getValue(String::class.java)?:""
                    val location = "$latitude, $longitude"
                    data.list.add(SingleLocationCriticalWeatherPhenomenonData(alertID, location, emLevel, message, imageURL, timeStamp))
                }
                onComplete(true, data, "Success")
            } else {
                onComplete(false, null, "No alert found for $phenomenon at $location")
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onComplete(false, null, "Error fetching data: ${databaseError.message}")
        }
    })
}

fun getSpecificAlertByPhenomenonAndLocationForMaps(phenomenon: String, location: String, onComplete: (Boolean, List<LocationData>?, String?) -> Unit) {
    val db = storageRef()
    val ref = db.getReference("alertsByPhenomenonAndLocationLast24h").child(phenomenon).child(location)

    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val data = mutableListOf<LocationData>()
                for (snapshot in dataSnapshot.children) {
                    val latitude = snapshot.child("location").child("latitude").getValue(Double::class.java)?:0.0
                    val longitude = snapshot.child("location").child("longitude").getValue(Double::class.java)?:0.0
                    val location = LocationData(latitude, longitude)
                    data.add(location)
                }
                onComplete(true, data, null)
            } else {
                onComplete(false, null, "No alert found for $phenomenon at $location")
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onComplete(false, null, "Error fetching data: ${databaseError.message}")
        }
    })
}