package com.rstit.connector.model.chat

import com.rstit.connector.model.inbox.Message

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
data class ChatEntry(val message: Message,
                     val avatar: String?)