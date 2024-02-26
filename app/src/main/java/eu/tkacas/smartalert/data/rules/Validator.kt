package eu.tkacas.smartalert.data.rules


object Validator {
    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            (fName.isNotEmpty() && fName.length >= 2)
        )

    }

    fun validateLastName(lName: String): ValidationResult {
        return ValidationResult(
            (lName.isNotEmpty() && lName.length >= 2)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (email.isNotEmpty())
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 4)
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidationResult {
        return ValidationResult(
            statusValue
        )
    }

data class ValidationResult(
    val status: Boolean = false
)

}