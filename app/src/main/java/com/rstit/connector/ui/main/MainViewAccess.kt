package com.rstit.connector.ui.main

import com.rstit.connector.ui.base.MultiViewAdapter

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface MainViewAccess {
    val adapter: MultiViewAdapter

    fun notifyDataSetChanged()

    fun selectPerson()

    fun writeToAll()

    fun closeKeyboard()

    fun displaySuccessSnackbar()

    fun displayErrorMessage()

    fun clearScrollListener()

    fun setScrollListenerEnabled(enabled: Boolean)

    fun navigateToProfile()
}