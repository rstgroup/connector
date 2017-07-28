package com.rstit.connector.model.user

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */

enum class UserRole(val role: String) {
    Developer("developer"),
    Admin("admin");

    companion object {
        fun from(role: String?): UserRole =
                UserRole.values().firstOrNull { it.role.toLowerCase() == role?.toLowerCase() ?: "" }
                        ?: UserRole.Developer
    }
}