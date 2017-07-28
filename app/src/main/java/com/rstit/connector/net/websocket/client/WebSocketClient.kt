package com.rstit.connector.net.websocket.client

import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocketFrame
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.websocket.WSEvent
import com.rstit.connector.model.websocket.WSMessage
import com.rstit.connector.net.websocket.ConnectionState
import com.rstit.connector.net.websocket.error.WSError
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * @author Tomasz Trybala & Marcin Przepiorkowski
 * @since 2017-07-27
 */
class WebSocketClient(val gson: Gson) : WebSocket {
    override val connectionStateStream: BehaviorSubject<ConnectionState> = BehaviorSubject.create()
    override val errorStream: PublishSubject<Throwable> = PublishSubject.create()
    override val newMessageStream: PublishSubject<WSMessage> = PublishSubject.create()
    override val messageReadStream: PublishSubject<WSMessage> = PublishSubject.create()
    override val messageSuccessStream: PublishSubject<WSMessage> = PublishSubject.create()

    var webSocket: com.neovisionaries.ws.client.WebSocket? = null
    var webSocketUri: String? = null

    private fun handleMessage(text: String) {
        try {
            val message = gson.fromJson(text, WSMessage::class.java)
            when (WSEvent.from(message.eventKey)) {
                WSEvent.ReceiveNewMessage -> newMessageStream.onNext(message)
                WSEvent.ReceiveReadMessage -> messageReadStream.onNext(message)
                WSEvent.SendNewMessageSuccess -> messageSuccessStream.onNext(message)
                else -> errorStream.onNext(WSError())
            }
        } catch (e: Exception) {
            errorStream.onNext(e)
        }
    }

    private fun initialize(uri: String) {
        webSocketUri = uri
        webSocket = WebSocketFactory().createSocket(webSocketUri)
        webSocket?.addListener(object : WebSocketAdapter() {
            override fun onConnected(websocket: com.neovisionaries.ws.client.WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
                connectionStateStream.onNext(ConnectionState.Connected)
            }

            override fun onConnectError(websocket: com.neovisionaries.ws.client.WebSocket?, exception: WebSocketException?) {
                connectionStateStream.onNext(ConnectionState.ConnectionError)
            }

            override fun onDisconnected(websocket: com.neovisionaries.ws.client.WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
                connectionStateStream.onNext(ConnectionState.Disconnected)
            }

            override fun onPongFrame(websocket: com.neovisionaries.ws.client.WebSocket?, frame: WebSocketFrame?) {
                connectionStateStream.onNext(ConnectionState.PongReceived)
            }

            override fun onTextMessage(websocket: com.neovisionaries.ws.client.WebSocket?, text: String?) {
                text?.let { handleMessage(it) }
            }

            override fun onSendError(websocket: com.neovisionaries.ws.client.WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
                errorStream.onNext(cause ?: WSError())
            }

            override fun onUnexpectedError(websocket: com.neovisionaries.ws.client.WebSocket?, cause: WebSocketException?) {
                errorStream.onNext(cause ?: WSError())
            }
        })
    }

    override fun connect(uri: String) {
        initialize(uri)
        connectionStateStream.onNext(ConnectionState.Connecting)
        webSocket?.connectAsynchronously()
    }

    override fun disconnect() {
        connectionStateStream.onNext(ConnectionState.Disconnecting)
        webSocket?.disconnect()
    }

    override fun reconnect() {
        webSocketUri?.let {
            disconnect()
            connect(it)
        }
    }

    override fun isConnected(): Boolean {
        val isOpen = webSocket != null && (webSocket?.isOpen ?: false)
        return isOpen && (connectionStateStream.value == ConnectionState.Connected ||
                connectionStateStream.value == ConnectionState.PongReceived)
    }

    override fun ping() {
        webSocket?.sendPing()
    }

    override fun sendNewMessage(uuid: String, message: Message) {
        webSocket?.sendText(gson.toJson(
                WSMessage(eventKey = WSEvent.SendNewMessage.type, uuid = uuid, message = message)))
    }

    override fun sendReadMessage(messageId: Long) {
        webSocket?.sendText(gson.toJson(
                WSMessage(eventKey = WSEvent.SendReadMessage.type, id = messageId)))
    }
}