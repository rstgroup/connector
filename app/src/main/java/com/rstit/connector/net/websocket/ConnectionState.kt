package com.rstit.connector.net.websocket

/**
 * @author Tomasz Trybala & Marcin Przepiorkowski
 * @since 2017-07-27
 */
enum class ConnectionState {
    Connecting,
    Connected,
    ConnectionError,
    Disconnecting,
    Disconnected,
    PongReceived
}