package com.rstit.connector.util

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.Observable

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
class SimpleTextWatcher : TextWatcher {
    private interface TextChangeListener {
        fun onTextChanged(text: String)
    }

    private val textListeners = ArrayList<TextChangeListener>()

    val textObservable: Observable<String> = Observable.create({ e ->
        textListeners.add(
                object : TextChangeListener {
                    override fun onTextChanged(text: String) = e.onNext(text)
                })
    })

    override fun afterTextChanged(p0: Editable?) {
        for (listener in textListeners) {
            listener.onTextChanged(p0.toString())
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        /*no-op*/
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        /*no-op*/
    }
}