package com.rstit.connector.di.fcm

import com.rstit.connector.di.base.scope.ServiceScope
import com.rstit.connector.fcm.RstFirebaseMessageService
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-26
 */
@ServiceScope
@Subcomponent(modules = arrayOf(FcmModule::class))
interface FcmComponent {
    fun inject(service: RstFirebaseMessageService): RstFirebaseMessageService
}