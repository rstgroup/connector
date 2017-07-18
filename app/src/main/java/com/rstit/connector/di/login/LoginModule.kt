package com.rstit.connector.di.login

import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.ui.login.LoginFragment
import com.rstit.ui.auth.login.LoginViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Module
class LoginModule(private val fragment: LoginFragment) {
    @Provides
    @FragmentScope
    fun provideLoginFragment(): LoginFragment {
        return fragment
    }

    @Provides
    @FragmentScope
    fun provideLoginViewAccess(fragment: LoginFragment): LoginViewAccess {
        return fragment
    }
}