package com.rstit.connector.di.password

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.password.ResetPasswordViewModelTest
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Subcomponent(modules = arrayOf(TestResetPasswordModule::class))
interface TestResetPasswordComponent {
    fun inject(test: ResetPasswordViewModelTest): ResetPasswordViewModelTest
}