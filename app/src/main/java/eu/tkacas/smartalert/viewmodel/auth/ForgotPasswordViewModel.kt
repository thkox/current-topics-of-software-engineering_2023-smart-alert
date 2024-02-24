package eu.tkacas.smartalert.viewmodel.auth

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.data.rules.Validator
import eu.tkacas.smartalert.ui.event.ForgotPasswordUIEvent
import eu.tkacas.smartalert.ui.state.ForgotPasswordUIState
import eu.tkacas.smartalert.cloud.sendPasswordResetEmail

class ForgotPasswordViewModel : ViewModel() {

    private val TAG = ForgotPasswordViewModel::class.simpleName

    var navController: NavController? = null

    var forgotPasswordUIState = mutableStateOf(ForgotPasswordUIState())

    var validationPassed = mutableStateOf(false)

    private fun validateForgotPasswordUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = forgotPasswordUIState.value.email
        )

        forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
            emailError = emailResult.status
        )

        validationPassed.value = emailResult.status
    }

    fun onEvent(event: ForgotPasswordUIEvent) {
        when (event) {
            is ForgotPasswordUIEvent.EmailChanged -> {
                forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
                    email = event.email
                )
            }

            is ForgotPasswordUIEvent.ResetPasswordButtonClicked -> {
                resetPassword()
            }
        }
        validateForgotPasswordUIDataWithRules()
    }

    private fun resetPassword() {
        val email = forgotPasswordUIState.value.email
        sendPasswordResetEmail(email, navController)
    }
}