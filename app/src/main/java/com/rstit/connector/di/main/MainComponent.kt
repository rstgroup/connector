package com.rstit.connector.di.main

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainActivity
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity): MainActivity
}