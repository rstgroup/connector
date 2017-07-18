package com.rstit.connector.ui.login

import android.support.annotation.StringRes
import com.rstit.ui.auth.login.LoginViewAccess

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface CustomLoginViewAccess: LoginViewAccess {
    fun closeKeyboard()

    fun displayErrorMessage(@StringRes error: Int)

    fun navigateToMain()
}