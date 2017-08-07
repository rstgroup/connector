package com.rstit.connector.util

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rstit.connector.R
import com.rstit.connector.model.inbox.MessageStatus
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@BindingAdapter("visibleIf")
fun changeVisibility(view: View?, visible: Boolean) {
    view?.let { it.visibility = if (visible) View.VISIBLE else View.GONE }
}

@BindingAdapter("invisibleIf")
fun changeInvisibility(view: View?, visible: Boolean) {
    view?.let { it.visibility = if (visible) View.INVISIBLE else View.VISIBLE }
}

@BindingAdapter("chatStatus")
fun changeChatIcon(view: ImageView?, status: MessageStatus) {
    view?.apply {
        visibility = if (status != MessageStatus.Sending && status != MessageStatus.Error) View.VISIBLE else View.GONE
        setImageResource(if (status == MessageStatus.Error) R.drawable.ic_alert else R.drawable.ic_check_all)
        setColorFilter(ContextCompat.getColor(context,
                if (status == MessageStatus.Error)
                    android.R.color.holo_red_dark
                else if (status == MessageStatus.Read)
                    R.color.colorAccent
                else
                    android.R.color.darker_gray))
    }
}

@BindingAdapter("animateLabel")
fun animateLabelVisibility(view: View?, visible: Boolean) {
    view?.let {
        if(visible) ChatUtil.expandView(it)else ChatUtil.collapseView(it) }
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

@BindingAdapter("rightTransitionVisibility")
fun translateView(view: View?, visible: Boolean) {
    view?.let {
        val anim = AnimationUtils.loadAnimation(it.context, if (visible) R.anim.slide_from_right else R.anim.slide_in_right).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    /*no-op*/
                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (!visible) view.visibility = View.GONE
                }

                override fun onAnimationStart(p0: Animation?) {
                    if (visible) view.visibility = View.VISIBLE
                }
            })
        }
        it.startAnimation(anim)
    }
}

@BindingAdapter("boldIf")
fun setBoldIf(textView: TextView?, value: Boolean) {
    textView?.setTypeface(null, if (value) Typeface.BOLD else Typeface.NORMAL)
}
