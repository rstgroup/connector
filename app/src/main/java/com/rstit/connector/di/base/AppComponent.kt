package com.rstit.connector.di.base

import android.app.Application
import com.rstit.connector.di.auth.AuthComponent
import com.rstit.connector.di.auth.AuthModule
import com.rstit.connector.di.login.LoginComponent
import com.rstit.connector.di.login.LoginModule
import com.rstit.connector.di.main.MainComponent
import com.rstit.connector.di.main.MainModule
import com.rstit.di.base.ValidationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ValidationModule::class, AppSettingsModule::class, NetModule::class))
interface AppComponent {
    fun inject(app: Application)

    fun plus(module: LoginModule): LoginComponent

    fun plus(module: AuthModule): AuthComponent

    fun plus(module: MainModule): MainComponent
}