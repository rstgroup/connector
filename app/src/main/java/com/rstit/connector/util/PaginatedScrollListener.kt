package com.rstit.connector.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
const val VISIBLE_THRESHOLD = 5

abstract class PaginatedScrollListener : RecyclerView.OnScrollListener() {
    abstract fun fetchData()

    private var previousTotalItemCount = 0
    private var loading = true
    var enabled = true

    fun clear() {
        previousTotalItemCount = 0
        loading = true
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        recyclerView?.let {
            val firstVisibleItem = (it.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val visibleItemCount = it.childCount
            val totalItemCount = it.layoutManager.itemCount

            if (totalItemCount < previousTotalItemCount) {
                previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) loading = true
            }

            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

            if (enabled && !loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                fetchData()
                loading = true
            }
        }
    }
}