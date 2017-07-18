package com.rstit.connector.di.base

import android.content.Context
import com.rstit.connector.settings.AppSettings
import com.rstit.connector.settings.AppSettingsImpl
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@Module
class AppSettingsModule {
    @Provides
    fun provideAppSettings(context: Context): AppSettings {
        return AppSettingsImpl(context)
    }
}