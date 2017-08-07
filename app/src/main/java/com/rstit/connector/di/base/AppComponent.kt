package com.rstit.connector.di.base

import android.app.Application
import com.rstit.connector.di.auth.AuthComponent
import com.rstit.connector.di.auth.AuthModule
import com.rstit.connector.di.chat.ChatComponent
import com.rstit.connector.di.chat.ChatModule
import com.rstit.connector.di.date.DateModule
import com.rstit.connector.di.fcm.FcmComponent
import com.rstit.connector.di.fcm.FcmModule
import com.rstit.connector.di.login.LoginComponent
import com.rstit.connector.di.login.LoginModule
import com.rstit.connector.di.main.MainComponent
import com.rstit.connector.di.main.MainModule
import com.rstit.connector.di.password.ResetPasswordComponent
import com.rstit.connector.di.password.ResetPasswordModule
import com.rstit.connector.di.search.UserSearchComponent
import com.rstit.connector.di.search.UserSearchModule
import com.rstit.di.base.ValidationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ValidationModule::class, AppSettingsModule::class, NetModule::class,
        DateModule::class, WebSocketModule::class))
interface AppComponent {
    fun inject(app: Application)

    fun plus(module: LoginModule): LoginComponent

    fun plus(module: AuthModule): AuthComponent

    fun plus(module: MainModule): MainComponent

    fun plus(module: ResetPasswordModule): ResetPasswordComponent

    fun plus(module: UserSearchModule): UserSearchComponent

    fun plus(module: ChatModule): ChatComponent

    fun plus(module: FcmModule): FcmComponent
}