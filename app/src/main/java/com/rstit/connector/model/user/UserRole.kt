package com.rstit.connector.model.user

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */

enum class UserRole(val role: String) {
    Developer("developer"),
    Admin("admin");

    companion object {
        fun from(role: String?): UserRole {
            for (enumRole in UserRole.values()) {
                if (enumRole.role.toLowerCase() == role?.toLowerCase() ?: "") {
                    return enumRole
                }
            }

            return UserRole.Developer
        }
    }
}