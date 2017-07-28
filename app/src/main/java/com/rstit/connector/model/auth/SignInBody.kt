package com.rstit.connector.model.auth

import com.google.gson.annotations.SerializedName

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
data class SignInBody(
        @SerializedName("email")
        val email: String,

        @SerializedName("password")
        val password: String
)
