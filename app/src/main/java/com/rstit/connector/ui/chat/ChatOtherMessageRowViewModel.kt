package com.rstit.connector.ui.chat

import com.rstit.binding.ObservableString
import com.rstit.connector.date.DateConverter
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.ui.base.RowViewModel
import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
class ChatOtherMessageRowViewModel(message: Message, avatar: String?, converter: DateConverter) : RowViewModel() {
    val content = ObservableString(message.content)
    val avatar = ObservableString(avatar)
    val time = ObservableString(converter.convert(message.createdAt ?: Date()))
}