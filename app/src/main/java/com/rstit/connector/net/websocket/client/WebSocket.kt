package com.rstit.connector.net.websocket.client

import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.websocket.WSMessage
import com.rstit.connector.net.websocket.ConnectionState
import io.reactivex.Observable


/**
 * @author Tomasz Trybala
 * @since 2017-07-27
 */
interface WebSocket {
    fun connect(uri: String)

    fun disconnect()

    fun reconnect()

    fun isConnected(): Boolean

    fun ping()

    fun sendNewMessage(uuid: String, message: Message)

    fun sendReadMessage(messageId: Long)

    val connectionStateStream: Observable<ConnectionState>

    val errorStream: Observable<Throwable>

    val newMessageStream: Observable<WSMessage>

    val messageReadStream: Observable<WSMessage>

    val messageSuccessStream: Observable<WSMessage>
}