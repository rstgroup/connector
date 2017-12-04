package com.rstit.connector.di.search

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.search.UserSearchViewModelTest
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Subcomponent(modules = arrayOf(TestUserSearchModule::class))
interface TestUserSearchComponent {
    fun inject(test: UserSearchViewModelTest): UserSearchViewModelTest
}