package com.rstit.connector.ui.search

import com.rstit.connector.ui.base.MultiViewAdapter
import com.rstit.connector.util.SimpleTextWatcher

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
interface UserSearchViewAccess {
    fun startSpeaking()

    fun finishActivity()

    val adapter: MultiViewAdapter

    val textWatcher: SimpleTextWatcher

    fun notifyDataSetChanged(previousSize: Int, currentSize: Int)
}