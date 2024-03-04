package eu.tkacas.smartalert.ui.state

data class ForgotPasswordUIState(
    var email: String = "",
    var emailError: Boolean = false
)
