package com.rstit.connector.ui.chat

import com.rstit.binding.ObservableString
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.ui.base.RowViewModel

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
class ChatOtherMessageRowViewModel(message: Message, avatar: String) : RowViewModel() {
    val content = ObservableString(message.content)
    val avatar = ObservableString(avatar)
}