package com.example.listify.data

// ---------------------------------------------------------
// UserRole ENUM
// Represents the current authentication role of the user.
// Used for:
// - Admin access control
// - Role-based UI rendering
// - Navigation restrictions
// ---------------------------------------------------------
enum class UserRole {

    USER,     // Normal user
    ADMIN,    // Administrator
    NONE      // Not logged in
}

// ---------------------------------------------------------
// AuthManager (Simple Demo Authentication Manager)
// Handles login, logout, and role tracking.
//
// NOTE:
// This is NOT secure authentication.
// It is only for demo/testing purposes.
// Credentials are hardcoded for temporary use
// ---------------------------------------------------------
object AuthManager {

    // Current logged-in role
    // private set prevents external modification
    var role: UserRole = UserRole.NONE
        private set

    // ---------------------------------------------------------
    // Demo credentials (used in LoginScreen)
    // ---------------------------------------------------------
    private const val ADMIN_EMAIL = "admin@listify.com"
    private const val ADMIN_PASSWORD = "Admin123"

    private const val USER_EMAIL = "user@listify.com"
    private const val USER_PASSWORD = "User123"

    // ---------------------------------------------------------
    // loginUser()
    // Validates normal user credentials.
    // If correct → role becomes USER
    // If incorrect → role resets to NONE
    // ---------------------------------------------------------
    fun loginUser(email: String, password: String): Boolean {

        val ok = email == USER_EMAIL && password == USER_PASSWORD

        role = if (ok)
            UserRole.USER
        else
            UserRole.NONE

        return ok
    }

    // ---------------------------------------------------------
    // loginAdmin()
    // Validates admin credentials.
    // If correct → role becomes ADMIN
    // If incorrect → role resets to NONE
    // ---------------------------------------------------------
    fun loginAdmin(email: String, password: String): Boolean {

        val ok = email == ADMIN_EMAIL && password == ADMIN_PASSWORD

        role = if (ok)
            UserRole.ADMIN
        else
            UserRole.NONE

        return ok
    }

    // ---------------------------------------------------------
    // logout()
    // Clears the current session by resetting role.
    // ---------------------------------------------------------
    fun logout() {
        role = UserRole.NONE
    }
}
