package com.rstit.connector.model.password

import com.google.gson.annotations.SerializedName

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
data class ChangePasswordBody(
        @SerializedName("old_password")
        val oldPassword: String,

        @SerializedName("new_password")
        val newPassword: String
)
