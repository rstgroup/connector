package com.rstit.connector.di.base

import com.rstit.connector.di.auth.TestAuthComponent
import com.rstit.connector.di.auth.TestAuthModule
import com.rstit.connector.di.login.TestCustomLoginComponent
import com.rstit.connector.di.login.TestCustomLoginModule
import com.rstit.di.base.ValidationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Singleton
@Component(modules = arrayOf(TestAppModule::class, TestAppSettingsModule::class, TestNetModule::class,
        ValidationModule::class
))
interface TestAppComponent {
    fun plus(module: TestAuthModule): TestAuthComponent

    fun plus(module: TestCustomLoginModule): TestCustomLoginComponent
}

