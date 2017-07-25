package com.rstit.connector.ui.chat

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import java.util.*
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
class ChatViewModel @Inject constructor() : BaseViewModel() {
    val isConnected = ObservableBoolean(true)
    val loading = ObservableBoolean()
    val isEmpty = ObservableBoolean()
    val content = ObservableString()
    val models = ArrayList<RowViewModel>()

    @Inject
    lateinit var viewAccess: ChatViewAccess

    fun sendMessage() {
        models.add(0, ChatMyMessageRowViewModel(Message(content = content.get(),
                createdAt = Date(), isMyMessage = true)))
        content.set("")
        viewAccess.notifyItemInserted()
    }
}