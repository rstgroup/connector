package com.rstit.connector.di.search

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainActivity
import com.rstit.connector.ui.search.UserSearchActivity
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Subcomponent(modules = arrayOf(UserSearchModule::class))
interface UserSearchComponent {
    fun inject(activity: UserSearchActivity): UserSearchActivity
}