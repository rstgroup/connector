package com.rstit.connector.model.password

import com.google.gson.annotations.SerializedName

/**
 * @author Tomasz Trybala
 * @since 2017-07-27
 */
data class ChangePasswordResponse(
        @SerializedName("token")
        val token: String? = null
)
