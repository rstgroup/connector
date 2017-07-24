package com.rstit.connector.di.base

import com.rstit.connector.di.auth.TestAuthComponent
import com.rstit.connector.di.auth.TestAuthModule
import com.rstit.connector.di.login.TestCustomLoginComponent
import com.rstit.connector.di.login.TestCustomLoginModule
import com.rstit.connector.di.main.TestMainComponent
import com.rstit.connector.di.main.TestMainModule
import com.rstit.connector.di.password.TestResetPasswordComponent
import com.rstit.connector.di.password.TestResetPasswordModule
import com.rstit.connector.di.search.TestUserSearchComponent
import com.rstit.connector.di.search.TestUserSearchModule
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

    fun plus(module: TestResetPasswordModule): TestResetPasswordComponent

    fun plus(module: TestUserSearchModule): TestUserSearchComponent

    fun plus(module: TestMainModule): TestMainComponent
}

