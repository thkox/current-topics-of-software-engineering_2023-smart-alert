package eu.tkacas.smartalert.ui.event

sealed class ForgotPasswordUIEvent {
    data class EmailChanged(val email: String) : ForgotPasswordUIEvent()
    data object ResetPasswordButtonClicked : ForgotPasswordUIEvent()
}