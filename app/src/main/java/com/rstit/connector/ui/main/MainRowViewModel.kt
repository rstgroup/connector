package com.rstit.connector.ui.main

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.model.inbox.MessageStatus
import com.rstit.connector.ui.base.RowViewModel

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class MainRowViewModel(val entry: InboxEntry) : RowViewModel() {
    val name = ObservableString("${entry.user?.name} ${entry.user?.lastName}")
    val lastName = ObservableString(entry.lastMessage?.content)
    val avatar = ObservableString(entry.user?.avatar)
    val isRead = ObservableBoolean(isMessageRead())

    fun isMessageRead(): Boolean {
        entry.lastMessage?.let {
            return@isMessageRead it.isMyMessage != null && it.isMyMessage.and(MessageStatus.from(it.status) == MessageStatus.Sent)
        }
        return false
    }
}