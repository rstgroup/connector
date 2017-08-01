package com.rstit.connector.ui.chat

import com.rstit.connector.date.DateConverter
import com.rstit.connector.model.inbox.Message

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
class ChatOtherMessageRowViewModel(message: Message, converter: DateConverter) : BaseChatMessageRowViewModel(message, converter)