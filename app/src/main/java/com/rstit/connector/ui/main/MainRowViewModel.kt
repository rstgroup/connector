package com.rstit.connector.ui.main

import com.rstit.binding.ObservableString
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.ui.base.RowViewModel
import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class MainRowViewModel(val entry: InboxEntry) : RowViewModel() {
    val name: ObservableString = ObservableString(String.format(Locale.getDefault(),
            "%s %s", entry.user?.name, entry.user?.lastName))
    val lastName: ObservableString = ObservableString(entry.lastMessage?.content)
    val avatar: ObservableString = ObservableString(entry.user?.avatar)
}