package com.rstit.connector.ui.search

import com.rstit.binding.ObservableString
import com.rstit.connector.model.user.User
import com.rstit.connector.ui.base.RowViewModel
import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
class UserRowViewModel(user: User) : RowViewModel() {
    val name = ObservableString(String.format(Locale.getDefault(), "%s %s", user.name, user.lastName))
    val avatar = ObservableString(user.avatar)
}