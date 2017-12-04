package com.rstit.connector.di.login

import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.ui.login.LoginFragment
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@FragmentScope
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {
    fun inject(loginFragment: LoginFragment): LoginFragment
}