package com.rstit.connector.ui.chat

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.date.DateConverter
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.di.date.names.ChatConverter
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.user.User
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
class ChatViewModel @Inject constructor() : BaseViewModel() {
    val isConnected = ObservableBoolean(true)
    val loading = ObservableBoolean(true)
    val isEmpty = ObservableBoolean()
    val content = ObservableString()
    val models = ArrayList<RowViewModel>()

    @Inject
    lateinit var viewAccess: ChatViewAccess

    @field:[Inject ChatConverter]
    lateinit var chatDateConverter: DateConverter

    @Inject
    lateinit var api: ConnectorApi

    lateinit var otherUser: User

    fun refresh() {
        registerDisposable(api.getChatAfterMessage(otherUser.id, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.set(true) }
                .doOnTerminate { loading.set(false) }
                .doOnNext { response -> viewAccess.setScrollListenerEnabled(!response.isLastPage) }
                .map { response -> response.entries ?: Collections.emptyList() }
                .flatMap { list -> Observable.fromIterable(list) }
                .map { entry ->
                    if (entry.isMyMessage ?: false)
                        ChatMyMessageRowViewModel(entry, chatDateConverter)
                    else
                        ChatOtherMessageRowViewModel(entry, otherUser.avatar, chatDateConverter)
                }
                .toList()
                .subscribe({ list -> handleResponse(list, true) }, { handleError() }))
    }

    fun sendMessage() {
        models.add(0, ChatMyMessageRowViewModel(Message(content = content.get(), createdAt = Date(), isMyMessage = true), chatDateConverter))
        content.set("")
        isEmpty.set(false)
        viewAccess.notifyDataRangeChanged(0, 1)
    }

    fun handleResponse(list: Collection<RowViewModel>, clear: Boolean) {
        if (clear) models.clear()
        models.addAll(list)
        isEmpty.set(models.isEmpty())
        viewAccess.notifyDataRangeChanged(if (clear) 0 else models.size - list.size, list.size)
    }

    fun handleError() = isEmpty.set(models.isEmpty())
}