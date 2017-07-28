package com.rstit.connector.ui.search

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.ui.base.RowViewModel
import com.rstit.ui.base.model.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
const val THROTTLE_TIMEOUT = 500L //ms

@ActivityScope
class UserSearchViewModel @Inject constructor() : BaseViewModel() {
    val loading = ObservableBoolean()
    val isEmpty = ObservableBoolean()
    val isMicAvailable = ObservableBoolean()
    val searchText = ObservableString()
    val models: MutableList<RowViewModel> = ArrayList()

    @Inject
    lateinit var viewAccess: UserSearchViewAccess

    @Inject
    lateinit var api: ConnectorApi

    fun clearText() = searchText.set("")

    fun initSearching() =
            registerDisposable(viewAccess.textWatcher.textObservable
                    .throttleWithTimeout(THROTTLE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .filter { it -> !it.isNullOrBlank() }
                    .map { it -> it.toLowerCase() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::searchUser, { /*no-op*/ }))

    fun searchUser(name: String) {
        registerDisposable(api.searchUsers(name = name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { it -> Observable.fromIterable(it) }
                .map { it -> UserRowViewModel(it) }
                .toList()
                .toObservable()
                .doOnNext { it ->
                    models.clear()
                    models.addAll(it)
                }
                .doOnSubscribe { loading.set(true) }
                .doOnTerminate { loading.set(false) }
                .subscribe({ handleResponse(true) }, { handleResponse(false) }))
    }

    fun handleResponse(isSuccess: Boolean) {
        isEmpty.set(models.isEmpty())
        if (isSuccess) viewAccess.notifyDataSetChanged()
    }
}