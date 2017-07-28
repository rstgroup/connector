package com.rstit.connector.di.base

import com.google.gson.Gson
import com.rstit.connector.net.websocket.client.WebSocket
import com.rstit.connector.net.websocket.client.WebSocketClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Module
class WebSocketModule {
    @Provides
    @Singleton
    fun provideWebSocketClient(gson: Gson): WebSocket = WebSocketClient(gson)
}