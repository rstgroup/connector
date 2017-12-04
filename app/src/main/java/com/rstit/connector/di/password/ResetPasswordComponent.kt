package com.rstit.connector.di.password

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.password.ResetPasswordActivity
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ResetPasswordModule::class))
interface ResetPasswordComponent {
    fun inject(activity: ResetPasswordActivity): ResetPasswordActivity
}