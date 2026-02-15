package com.example.listify.data

enum class UserRole { USER, ADMIN, NONE }

object AuthManager {

    var role: UserRole = UserRole.NONE
        private set

    // ✅ Demo credentials (write these in your report + demo)
    private const val ADMIN_EMAIL = "admin@listify.com"
    private const val ADMIN_PASSWORD = "Admin123"

    private const val USER_EMAIL = "user@listify.com"
    private const val USER_PASSWORD = "User123"

    fun loginUser(email: String, password: String): Boolean {
        val ok = email == USER_EMAIL && password == USER_PASSWORD
        role = if (ok) UserRole.USER else UserRole.NONE
        return ok
    }

    fun loginAdmin(email: String, password: String): Boolean {
        val ok = email == ADMIN_EMAIL && password == ADMIN_PASSWORD
        role = if (ok) UserRole.ADMIN else UserRole.NONE
        return ok
    }

    fun logout() {
        role = UserRole.NONE
    }
}
