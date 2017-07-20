package com.rstit.connector.di.password

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.password.ResetPasswordActivity
import com.rstit.connector.ui.password.ResetPasswordViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Module
class ResetPasswordModule(private val activity: ResetPasswordActivity) {
    @Provides
    @ActivityScope
    fun provideResetPasswordActivity(): ResetPasswordActivity = activity

    @Provides
    @ActivityScope
    fun provideResetPasswordViewAccess(activity: ResetPasswordActivity): ResetPasswordViewAccess =
            activity
}