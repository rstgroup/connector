package com.rstit.connector.di.base

import android.app.Application
import com.rstit.di.base.ValidationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ValidationModule::class))
interface AppComponent {
    fun inject(app: Application)
}