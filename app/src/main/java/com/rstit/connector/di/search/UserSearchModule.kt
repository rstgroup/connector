package com.rstit.connector.di.search

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.main.MainActivity
import com.rstit.connector.ui.main.MainViewAccess
import com.rstit.connector.ui.search.UserSearchActivity
import com.rstit.connector.ui.search.UserSearchViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
@ActivityScope
@Module
class UserSearchModule(private val activity: UserSearchActivity) {
    @Provides
    @ActivityScope
    fun provideUserSearchActivity(): UserSearchActivity = activity

    @Provides
    @ActivityScope
    fun provideUserSearchViewAccess(activity: UserSearchActivity): UserSearchViewAccess = activity
}