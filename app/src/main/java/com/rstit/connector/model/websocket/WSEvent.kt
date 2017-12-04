package com.rstit.connector.model.websocket

/**
 * @author Tomasz Trybala
 * @since 2017-07-27
 */
enum class WSEvent(val type: String) {
    Unknown("error"),
    SendNewMessage("send_new_message"),
    SendNewMessageSuccess("send_new_message_success"),
    SendReadMessage("send_read_message"),
    ReceiveNewMessage("receive_new_message"),
    ReceiveReadMessage("receive_read_message");

    companion object {
        fun from(event: String?): WSEvent =
                WSEvent.values().firstOrNull { it.type.toLowerCase() == event?.toLowerCase() ?: "" }
                        ?: Unknown
    }
}