package com.rstit.connector.di.main

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainViewModelTest
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Subcomponent(modules = arrayOf(TestMainModule::class))
interface TestMainComponent {
    fun inject(test: MainViewModelTest): MainViewModelTest
}