package com.rstit.connector.ui.base

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rstit.connector.ConnectorApplication

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
open class BaseFragment : Fragment() {
    val appComponent by lazy {
        ConnectorApplication.get(activity).appComponent
    }

    fun hideKeyboard() {
        activity?.let {
            it.currentFocus?.let {
                it.clearFocus()
                val manager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    fun showSnackbar(view: View, @StringRes message: Int, delay: Int = Snackbar.LENGTH_SHORT) =
            Snackbar.make(view, message, delay).show()
}