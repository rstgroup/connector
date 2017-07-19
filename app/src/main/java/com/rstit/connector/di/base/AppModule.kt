package com.rstit.connector.di.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.rstit.connector.di.base.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Module
@ActivityScope
class AppModule(private var application: Application) {
    @Provides
    fun getContext(): Context = application.applicationContext

    @Provides
    fun getResources(): Resources = application.resources
}