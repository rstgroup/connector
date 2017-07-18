package com.rstit.connector.ui.auth

import com.rstit.connector.ui.login.LoginFragment
import com.rstit.ui.base.model.BaseViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class AuthViewModel @Inject constructor(): BaseViewModel() {
    @Inject
    lateinit var viewAccess: AuthViewAccess

    fun initFragment(){
        viewAccess.replaceFragment(LoginFragment())
    }
}