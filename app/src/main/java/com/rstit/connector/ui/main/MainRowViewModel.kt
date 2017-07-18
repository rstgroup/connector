package com.rstit.connector.ui.main

import com.rstit.binding.ObservableString
import com.rstit.connector.ui.base.RowViewModel

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class MainRowViewModel(name: String, lastName: String, avatar: String): RowViewModel() {
    val name: ObservableString = ObservableString(name)
    val lastName: ObservableString = ObservableString(lastName)
    val avatar: ObservableString = ObservableString(avatar)
}