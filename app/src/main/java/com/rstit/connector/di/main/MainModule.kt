package com.rstit.connector.di.main

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainActivity
import com.rstit.connector.ui.main.MainViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Module
class MainModule(private val activity: MainActivity) {
    @Provides
    @ActivityScope
    fun provideMainActivity(): MainActivity = activity

    @Provides
    @ActivityScope
    fun provideMainActivityAccess(activity: MainActivity): MainViewAccess = activity

}