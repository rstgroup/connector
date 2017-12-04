package com.rstit.connector.di.auth

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.auth.AuthActivity
import com.rstit.connector.ui.auth.AuthViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Module
class AuthModule(private val activity: AuthActivity) {
    @Provides
    @ActivityScope
    fun provideAuthActivity(): AuthActivity = activity

    @Provides
    @ActivityScope
    fun provideAuthViewAccess(activity: AuthActivity): AuthViewAccess = activity
}