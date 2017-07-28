package com.rstit.connector.di.password

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.ui.login.CustomLoginViewAccess
import com.rstit.connector.ui.password.ResetPasswordViewAccess
import com.rstit.ui.auth.login.LoginViewAccess
import dagger.Module
import dagger.Provides
import org.mockito.Mockito


/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestResetPasswordModule {
    @Provides
    @ActivityScope
    fun provideResetPasswordViewAccess(): ResetPasswordViewAccess = Mockito.mock(ResetPasswordViewAccess::class.java)
}