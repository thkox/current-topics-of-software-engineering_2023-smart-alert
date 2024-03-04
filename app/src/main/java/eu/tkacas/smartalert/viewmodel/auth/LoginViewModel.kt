package eu.tkacas.smartalert.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.data.rules.Validator
import eu.tkacas.smartalert.database.cloud.FirebaseUtils
import eu.tkacas.smartalert.ui.event.LoginUIEvent
import eu.tkacas.smartalert.ui.state.LoginUIState
import java.util.Locale

class LoginViewModel : ViewModel() {
    val firebase = FirebaseUtils()


    private val TAG = LoginViewModel::class.simpleName

    var navController: NavController? = null

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    val toastMessage = MutableLiveData<String?>()

    val refreshScreen = mutableStateOf(false)


    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }


    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        firebase.signInUser(email, password) { success, errorMessage ->
            loginInProgress.value = false
            if (success) {
                navController?.navigate("permissions")
            } else {
                // Handle the error message
                toastMessage.value = null
                val currentLanguage = Locale.getDefault().language
                val loginFailedMessage = when (currentLanguage) {
                    "en" -> {
                        "Login failed, Please check your credentials."
                    }
                    "el" -> {
                        "Η σύνδεση απέτυχε, Ελέγξτε τα διαπιστευτήριά σας."
                    }
                    else -> {
                        "Login failed, Please check your credentials."
                    }
                }
                toastMessage.value = loginFailedMessage
                refreshScreen.value = !refreshScreen.value
                Log.d(TAG, "Login failed: $errorMessage")
            }
        }
    }


//    private fun login() {
//        loginInProgress.value = true
//        val email = loginUIState.value.email
//        val password = loginUIState.value.password
//
//        val auth = FirebaseAuth.getInstance()
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                loginInProgress.value = false
//                if (task.isSuccessful) {
//                    navController?.navigate("permissions")
//                } else {
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        //toastMessage.value = null // Set to null first
//                        toastMessage.value = "Incorrect password. Please try again."
//                        Log.d(TAG, "Toast message sent")
//                    } else {
//                        // Handle other types of exceptions
//                        Log.d(TAG, "Login failed: ${task.exception?.message}")
//                    }
//                    loginInProgress.value = false
//                }
//            }
//    }


}