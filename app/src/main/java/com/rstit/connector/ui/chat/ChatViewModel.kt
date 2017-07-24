package com.rstit.connector.ui.chat

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
class ChatViewModel @Inject constructor() : BaseViewModel() {
    val isConnected = ObservableBoolean()
    val loading = ObservableBoolean()
    val isEmpty = ObservableBoolean()
    val content = ObservableString()
    val models = ArrayList<RowViewModel>()

    fun sendMessage() {

    }
}