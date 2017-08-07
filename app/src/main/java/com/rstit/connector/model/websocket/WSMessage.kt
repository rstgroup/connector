package com.rstit.connector.model.websocket

import com.google.gson.annotations.SerializedName
import com.rstit.connector.model.inbox.Message

/**
 * @author Tomasz Trybala
 * @since 2017-07-27
 */
data class WSMessage(
        @SerializedName("event_key")
        val eventKey: String? = WSEvent.SendNewMessage.type,

        @SerializedName("uuid")
        val uuid: String? = null,

        @SerializedName("message")
        val message: Message? = null,

        @SerializedName("id")
        val id: Long? = null
)