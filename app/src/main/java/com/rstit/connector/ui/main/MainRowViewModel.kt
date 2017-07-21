package com.rstit.connector.ui.main

import com.rstit.binding.ObservableString
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.ui.base.RowViewModel

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class MainRowViewModel(entry: InboxEntry): RowViewModel() {
    val name: ObservableString = ObservableString(entry.user?.name)
    val lastName: ObservableString = ObservableString(entry.lastMessage?.content)
    val avatar: ObservableString = ObservableString(entry.user?.avatar)
}