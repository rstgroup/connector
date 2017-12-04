package com.rstit.connector.di.login

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.ui.login.CustomLoginViewModelTest
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@FragmentScope
@Subcomponent(modules = arrayOf(TestCustomLoginModule::class))
interface TestCustomLoginComponent {
    fun inject(test: CustomLoginViewModelTest): CustomLoginViewModelTest
}