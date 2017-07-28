package com.rstit.connector.model.password

import com.google.gson.annotations.SerializedName

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
data class MessageToAllBody(
        @SerializedName("content")
        val content: String
)
