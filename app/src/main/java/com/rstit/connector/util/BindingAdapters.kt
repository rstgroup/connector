package com.rstit.connector.util

import android.databinding.BindingAdapter
import android.view.View

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@BindingAdapter("visibleIf")
fun changeVisibility(view: View?, visible: Boolean) {
    view?.let { it.visibility = if (visible) View.VISIBLE else View.GONE }
}
