package com.rstit.connector.model.user

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */

enum class UserRole(val role: String) {
    DEVELOPER("developer"),
    ADMIN("admin");

    companion object {
        fun from(role: String?): UserRole = try {
            UserRole.valueOf(role ?: "")
        } catch (e: IllegalArgumentException) {
            UserRole.DEVELOPER
        }
    }
}