package com.rstit.connector.di.login

import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.ui.login.CustomLoginViewAccess
import com.rstit.ui.auth.login.LoginViewAccess
import dagger.Module
import dagger.Provides
import org.mockito.Mockito


/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestCustomLoginModule {
    @Provides
    @FragmentScope
    fun provideCustomLoginViewAccess(): CustomLoginViewAccess = Mockito.mock(CustomLoginViewAccess::class.java)

    @Provides
    @FragmentScope
    fun provideLoginViewAccess(): LoginViewAccess = Mockito.mock(LoginViewAccess::class.java)
}