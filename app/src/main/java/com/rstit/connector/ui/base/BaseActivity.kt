package com.rstit.connector.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rstit.connector.ConnectorApplication

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    val appComponent by lazy {
        ConnectorApplication.get(applicationContext).appComponent
    }

    fun hideKeyboard() {
        currentFocus?.let {
            it.clearFocus()
            val manager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun showSnackbar(view: View, @StringRes message: Int, delay: Int = Snackbar.LENGTH_LONG,
                     actionId: Int, runnable: (View) -> Unit) =
            Snackbar.make(view, message, delay).setAction(actionId, runnable).show()

    fun showSnackbar(view: View, @StringRes message: Int, delay: Int = Snackbar.LENGTH_SHORT) =
            Snackbar.make(view, message, delay).show()
}
