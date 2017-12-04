package com.rstit.connector.di.auth

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.auth.AuthViewModelTest
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Subcomponent(modules = arrayOf(TestAuthModule::class))
interface TestAuthComponent {
    fun inject(test: AuthViewModelTest): AuthViewModelTest
}