package com.rstit.connector.ui.search

import com.rstit.connector.BaseTest
import com.rstit.connector.di.base.DaggerTestAppComponent
import com.rstit.connector.di.search.TestUserSearchModule
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * *
 * @since 2017-07-24
 */
class UserSearchViewModelTest : BaseTest() {
    @Inject
    lateinit var model: UserSearchViewModel

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .build()
                .plus(TestUserSearchModule())
                .inject(this)
    }

    @Test
    fun checkInitialState() {
        assertThat(model.searchText.get(), nullValue())
        assertThat(model.loading.get(), `is`(false))
        assertThat(model.isEmpty.get(), `is`(false))
        assertThat(model.isMicAvailable.get(), `is`(false))
    }

    @Test
    fun clearText() {
        model.searchText.set("custom text")
        model.clearText()
        assertThat(model.searchText.get().isNullOrEmpty(), `is`(true))
    }

    @Test
    fun handleResponse_Success() {
        model.handleResponse(true, 0)
        Mockito.verify(model.viewAccess).notifyDataSetChanged(0, 0)
    }

    @Test
    fun handleResponse_Error() {
        model.handleResponse(false, 0)
        Mockito.verifyZeroInteractions(model.viewAccess)
    }

    @Test
    fun searchUser() {
        Mockito.`when`(model.api.searchUsers(Matchers.anyInt(), Matchers.anyString())).thenReturn(Observable.just(listOf(user)))
        model.searchUser("tom")
        Mockito.verify(model.api).searchUsers(Matchers.anyInt(), Matchers.anyString())
    }
}