package eu.tkacas.smartalert.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.database.cloud.signInUser
import eu.tkacas.smartalert.ui.event.LoginUIEvent
import eu.tkacas.smartalert.ui.state.LoginUIState
import eu.tkacas.smartalert.data.rules.Validator

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var navController: NavController? = null

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)


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

        signInUser(email, password) { success, errorMessage ->
            loginInProgress.value = false
            if (success) {
                navController?.navigate("permissions")
            } else {
                // Handle the error message
                Log.d(TAG, "Login failed: $errorMessage")
            }
        }
    }
}