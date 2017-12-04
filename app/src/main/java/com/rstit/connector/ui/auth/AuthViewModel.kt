package com.rstit.connector.ui.auth

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.ui.base.model.BaseViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
class AuthViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var viewAccess: AuthViewAccess

    fun initFragment() = viewAccess.showSignIn()
}