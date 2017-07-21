package com.rstit.connector.di.auth

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.auth.AuthActivity
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Subcomponent(modules = arrayOf(AuthModule::class))
interface AuthComponent {
    fun inject(authActivity: AuthActivity): AuthActivity
}