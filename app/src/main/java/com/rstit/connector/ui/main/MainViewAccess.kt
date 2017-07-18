package com.rstit.connector.ui.main

import com.rstit.connector.ui.base.MultiViewAdapter

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface MainViewAccess {
    fun getAdapter(): MultiViewAdapter
}