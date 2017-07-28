package com.rstit.connector.di.auth

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.auth.AuthViewAccess
import dagger.Module
import dagger.Provides
import org.mockito.Mockito


/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestAuthModule {
    @Provides
    @ActivityScope
    fun provideAuthViewAccess(): AuthViewAccess = Mockito.mock(AuthViewAccess::class.java)
}