package eu.tkacas.smartalert.cloud

import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

fun userExists() : Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}

fun getUserID() : String {
    return FirebaseAuth.getInstance().currentUser?.uid ?: ""
}

fun userIsEmployee() : Boolean {
    return userExists() && FirebaseAuth.getInstance().currentUser?.email?.contains("@civilprotection.gr") == true
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

