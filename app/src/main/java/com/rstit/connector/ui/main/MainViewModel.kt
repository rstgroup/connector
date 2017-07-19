package com.rstit.connector.ui.main

import android.databinding.ObservableBoolean
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
 * @since 2017-07-18
 */
class MainViewModel @Inject constructor() : BaseViewModel() {
    val loading: ObservableBoolean = ObservableBoolean()
    val isEmpty: ObservableBoolean = ObservableBoolean()
    val models: MutableList<RowViewModel> = ArrayList()

    @Inject
    lateinit var viewAccess: MainViewAccess

    @Inject
    lateinit var api: ConnectorApi

    fun loadData() {
        val model1 = MainRowViewModel("Tomasz Trybała", "English and English-like: Latin (except Vietnamese), Greek, and Cyrillic scripts, supported by both Roboto and Noto. Roboto has been extended to completely cover all Latin, Greek, and Cyrillic characters as defined in Unicode 7.0. The number of supported characters has doubled from previous releases, from about 2000 to about 4000 characters.",
                "https://storage.googleapis.com/material-design/publish/material_v_11/assets/0B7WCemMG6e0VcDd2YmVFbDhCZHc/style_typography.png")
        val list = arrayListOf<RowViewModel>(model1, model1, model1)

        registerDisposable(Observable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({loading.set(true)})
                .doOnTerminate({loading.set(false)})
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
        isEmpty.set(models.isEmpty())
    }
}