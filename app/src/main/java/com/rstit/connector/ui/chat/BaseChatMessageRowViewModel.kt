package com.rstit.connector.ui.chat

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.rstit.binding.ObservableString
import com.rstit.connector.date.DateConverter
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.inbox.MessageStatus
import com.rstit.connector.ui.base.RowViewModel
import java.util.Date

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
open class BaseChatMessageRowViewModel(message: Message, converter: DateConverter) : RowViewModel() {
    val content = ObservableString(message.content)
    val time = ObservableString(converter.convert(message.createdAt ?: Date()))
    val timeVisible = ObservableBoolean()
    val status = ObservableField<MessageStatus>(MessageStatus.Sending)

    fun toggleTime() = timeVisible.set(!timeVisible.get())
}