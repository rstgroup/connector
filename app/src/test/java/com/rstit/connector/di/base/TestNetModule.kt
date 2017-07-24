package com.rstit.connector.di.base

import com.rstit.connector.net.ConnectorApi
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestNetModule {
    @Provides
    fun provideApi(): ConnectorApi = Mockito.mock(ConnectorApi::class.java)
}