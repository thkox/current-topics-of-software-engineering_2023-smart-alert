package eu.tkacas.smartalert.models

data class UserData(
    val id: Int,
    val role: RoleData,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

enum class RoleData {
    CITIZEN, EMPLOYEE, ADMIN
}