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
                    .map { it -> it.toLowerCase() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::searchUser, { /*no-op*/ }))

    fun searchUser(name: String) {
        val currentSize = models.size
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
                .subscribe({ handleResponse(true, currentSize) }, { handleResponse(false, currentSize) }))
    }

    fun handleResponse(isSuccess: Boolean, previousSize: Int) {
        isEmpty.set(models.isEmpty())
        if (isSuccess) viewAccess.notifyDataSetChanged(previousSize, models.size)
    }
}