package com.rstit.connector.di.main

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainViewAccess
import dagger.Module
import dagger.Provides
import org.mockito.Mockito


/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestMainModule {
    @Provides
    @ActivityScope
    fun provideMainViewAccess(): MainViewAccess = Mockito.mock(MainViewAccess::class.java)
}