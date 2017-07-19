package com.rstit.connector.ui.login

import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.ui.auth.login.LoginViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@FragmentScope
class CustomLoginViewModel @Inject constructor() : LoginViewModel() {
    val error: ObservableString = ObservableString()

    @Inject
    lateinit var viewAccess: CustomLoginViewAccess

    fun validate() {
        if (isInputDataValid) {
            error.set(null)
            viewAccess.closeKeyboard()
            viewAccess.navigateToMain()
        } else {
            error.set(if (mLoginError.get() != null) mLoginError.get() else mPasswordError.get())
        }
    }
}
