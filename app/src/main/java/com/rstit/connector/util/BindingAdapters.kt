package com.rstit.connector.util

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rstit.connector.R
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@BindingAdapter("visibleIf")
fun changeVisibility(view: View?, visible: Boolean) {
    view?.let { it.visibility = if (visible) View.VISIBLE else View.GONE }
}

@BindingAdapter("circleImage")
fun loadCircleImage(view: ImageView?, avatar: String?) {
    view?.let {
        Glide.with(it.context)
                .load(avatar)
                .placeholder(R.drawable.ic_account_circle_grey)
                .error(R.drawable.ic_account_circle_grey)
                .bitmapTransform(CropCircleTransformation(it.context))
                .into(view)
    }
}
