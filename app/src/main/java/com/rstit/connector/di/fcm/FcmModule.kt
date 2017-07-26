package com.rstit.connector.di.fcm

import com.rstit.connector.di.base.scope.ServiceScope
import com.rstit.connector.fcm.RstFirebaseMessageService
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-26
 */
@ServiceScope
@Module
class FcmModule(private val service: RstFirebaseMessageService) {
    @Provides
    @ServiceScope
    fun provideRstFirebaseMessageService(): RstFirebaseMessageService = service
}