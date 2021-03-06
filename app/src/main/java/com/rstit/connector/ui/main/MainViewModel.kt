package com.rstit.connector.ui.main

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.model.password.MessageToAllBody
import com.rstit.connector.model.user.UserRole
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.settings.AppSettings
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
const val KEYBOARD_DELAY = 500L
const val ANIMATION_DELAY = 200L

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

    fun singOut() = appSettings.logOut()

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
                .doOnNext { viewAccess.setScrollListenerEnabled(!it.isLastPage) }
                .map { it.entries ?: emptyList() }
                .flatMap { Observable.fromIterable(it) }
                .map { MainRowViewModel(it) }
                .toList()
                .subscribe({ handleModels(it, true) }, { handleError() }))
    }

    fun handleModels(list: Collection<RowViewModel>, clear: Boolean) {
        if (clear) models.clear()
        models.addAll(list)
        isEmpty.set(models.isEmpty())
        viewAccess.notifyDataSetChanged()
    }

    fun handleError() {
        isEmpty.set(models.isEmpty())
    }

    fun sendMessageToAll() {
        registerDisposable(api.sendMessageToAll(MessageToAllBody(messageToAll.get()))
                .delay(KEYBOARD_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    isSendingMessage.set(true)
                    viewAccess.closeKeyboard()
                })
                .doOnTerminate({ isSendingMessage.set(false) })
                .subscribe(this::handleMessageResponse, { viewAccess.displayErrorMessage() }))
    }

    fun handleMessageResponse(response: Response<Void>) =
            if (response.isSuccessful) displaySuccess() else viewAccess.displayErrorMessage()

    fun displaySuccess() = registerDisposable(Observable.just(Any())
            .delay(ANIMATION_DELAY, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { hideMessage() }
            .subscribe({ viewAccess.displaySuccessSnackbar() }))

}