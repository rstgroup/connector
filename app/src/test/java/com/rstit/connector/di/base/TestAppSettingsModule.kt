package com.rstit.connector.di.base

import com.rstit.connector.settings.AppSettings
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestAppSettingsModule {
    @Provides
    fun provideAppSettings(): AppSettings = Mockito.mock(AppSettings::class.java)
}