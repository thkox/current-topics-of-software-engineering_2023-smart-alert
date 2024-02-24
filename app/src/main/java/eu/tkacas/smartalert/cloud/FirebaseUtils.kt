package eu.tkacas.smartalert.cloud

import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.tkacas.smartalert.models.ListOfLocationCriticalWeatherPhenomenonData
import eu.tkacas.smartalert.models.LocationCriticalWeatherPhenomenonData

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
    val ref = db.getReference("alertsByPhenomenonAndLocationCount").child(phenomenon) // Assuming a structure like 'alerts/<phenomenon>'

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

