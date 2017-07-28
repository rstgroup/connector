package com.rstit.connector.model.inbox

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */

enum class MessageStatus(val status: String) {
    Sending("sending"),
    Sent("sent"),
    Read("read"),
    Error("error");

    companion object {
        fun from(role: String?): MessageStatus =
                MessageStatus.values().firstOrNull { it.status.toLowerCase() == role?.toLowerCase() ?: "" }
                        ?: MessageStatus.Error
    }
}