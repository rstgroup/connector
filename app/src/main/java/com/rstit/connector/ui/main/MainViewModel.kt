package com.rstit.connector.ui.main

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.password.MessageToAllBody
import com.rstit.connector.model.user.User
import com.rstit.connector.model.user.UserRole
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.settings.AppSettings
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@ActivityScope
class MainViewModel @Inject constructor() : BaseViewModel() {
    val loading: ObservableBoolean = ObservableBoolean()
    val isEmpty: ObservableBoolean = ObservableBoolean()
    val isSendingMessage: ObservableBoolean = ObservableBoolean()
    val isChatAvailable: ObservableBoolean = ObservableBoolean()
    val isMessageVisible: ObservableBoolean = ObservableBoolean()
    val messageToAll = ObservableString()
    val models: MutableList<RowViewModel> = ArrayList()

    @Inject
    lateinit var viewAccess: MainViewAccess

    @Inject
    lateinit var api: ConnectorApi

    @Inject
    lateinit var appSettings: AppSettings

    fun hideMessage() {
        isMessageVisible.set(false)
        messageToAll.set("")
        viewAccess.closeKeyboard()
    }

    fun checkChatAvailability() = isChatAvailable.set(UserRole.from(appSettings.userStatus) == UserRole.Admin)

    fun showMessage() = isMessageVisible.set(true)

    fun refresh() = loadData(0, true)

    fun loadData(offset: Int = models.size, clear: Boolean) {
        registerDisposable(api.getInbox(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loading.set(true)
                    if (clear) viewAccess.clearScrollListener()
                }
                .doOnTerminate { loading.set(false) }
                .doOnNext { it -> viewAccess.setScrollListenerEnabled(!it.isLastPage) }
                .map { it -> it.entries ?: emptyList() }
                .flatMap { it -> Observable.fromIterable(it) }
                .map { it -> MainRowViewModel(it) }
                .toList()
                .subscribe({ models -> handleModels(models, true) }, { handleError() }))
    }

    fun handleModels(list: Collection<RowViewModel>, clear: Boolean) {
        if (clear) models.clear()
        models.addAll(list)
        isEmpty.set(models.isEmpty())
        viewAccess.notifyDataSetChanged()
    }

    fun handleError() {
        models.add(MainRowViewModel(InboxEntry(User(4, "Krzysztof", "Marszałek", "k.m@rst-it.com", "https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/3/005/05b/102/331e441.jpg", "admin"),
                Message(4, "Witam na pokładzie RST-IT!", "read", Date(), false))))
        viewAccess.setScrollListenerEnabled(false)
        viewAccess.notifyDataSetChanged()
        isEmpty.set(models.isEmpty())
    }

    fun sendMessageToAll() {
        registerDisposable(api.sendMessageToAll(
                MessageToAllBody(messageToAll.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    isSendingMessage.set(true)
                    viewAccess.closeKeyboard()
                })
                .doOnTerminate({ isSendingMessage.set(false) })
                .subscribe({ viewAccess.displaySuccessSnackbar() }, { viewAccess.displayErrorMessage() }))
    }
}