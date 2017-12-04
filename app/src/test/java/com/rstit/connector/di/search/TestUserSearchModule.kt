package com.rstit.connector.di.search

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.search.UserSearchViewAccess
import dagger.Module
import dagger.Provides
import org.mockito.Mockito


/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@Module
class TestUserSearchModule {
    @Provides
    @ActivityScope
    fun provideUserSearchViewAccess(): UserSearchViewAccess = Mockito.mock(UserSearchViewAccess::class.java)
}