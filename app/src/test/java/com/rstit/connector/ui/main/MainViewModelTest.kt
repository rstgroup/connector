package com.rstit.connector.ui.main

import com.rstit.connector.BaseTest
import com.rstit.connector.di.base.DaggerTestAppComponent
import com.rstit.connector.di.main.TestMainModule
import com.rstit.connector.model.user.UserRole
import com.rstit.connector.ui.base.RowViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * *
 * @since 2017-07-24
 */
class MainViewModelTest : BaseTest() {
    @Inject
    lateinit var model: MainViewModel

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .build()
                .plus(TestMainModule())
                .inject(this)
    }

    @Test
    fun checkInitialState() {
        assertThat(model.loading.get(), `is`(false))
        assertThat(model.isEmpty.get(), `is`(false))
        assertThat(model.isSendingMessage.get(), `is`(false))
        assertThat(model.isChatAvailable.get(), `is`(false))
        assertThat(model.isMessageVisible.get(), `is`(false))
        assertThat(model.messageToAll.get(), nullValue())
    }

    @Test
    fun hideMessage() {
        model.isMessageVisible.set(true)
        model.messageToAll.set("custom message")

        model.hideMessage()

        assertThat(model.isMessageVisible.get(), `is`(false))
        assertThat(model.messageToAll.get().isNullOrEmpty(), `is`(true))
        verify(model.viewAccess).closeKeyboard()
    }

    @Test
    fun checkChatAvailability_False() {
        model.checkChatAvailability()
        assertThat(model.isChatAvailable.get(), `is`(false))
    }

    @Test
    fun checkChatAvailability_True() {
        `when`(model.appSettings.userStatus).thenReturn(UserRole.Admin.role)
        model.checkChatAvailability()
        assertThat(model.isChatAvailable.get(), `is`(true))
    }

    @Test
    fun showMessage() {
        model.isMessageVisible.set(false)
        model.showMessage()
        assertThat(model.isMessageVisible.get(), `is`(true))
    }

    @Test
    fun handleError_NonEmpty() {
        model.models.add(RowViewModel())
        model.handleError()
        assertThat(model.isEmpty.get(), `is`(false))
    }

    @Test
    fun handleError_Empty() {
        model.models.clear()
        model.handleError()
        assertThat(model.isEmpty.get(), `is`(true))
    }

    @Test
    fun handleModels_NotClear() {
        model.models.add(RowViewModel())
        model.handleModels(listOf(RowViewModel()), false)
        assertThat(model.isEmpty.get(), `is`(false))
        assertThat(model.models.size, not(1))
        verify(model.viewAccess).notifyDataSetChanged()
    }

    @Test
    fun handleModels_Clear() {
        model.models.add(RowViewModel())
        model.handleModels(listOf(RowViewModel()), true)
        assertThat(model.isEmpty.get(), `is`(false))
        assertThat(model.models.size, `is`(1))
        verify(model.viewAccess).notifyDataSetChanged()
    }

    @Test
    fun sendMessageToAll() {
        model.messageToAll.set("custom message")
        `when`(model.api.sendMessageToAll(any())).thenReturn(Observable.just(response(true)))
        model.sendMessageToAll()
        verify(model.api).sendMessageToAll(any())
    }

    @Test
    fun loadDate() {
        `when`(model.api.getInbox(Matchers.anyInt(), Matchers.anyInt())).thenReturn(Observable.just(inboxResponse))
        model.loadData(0, clear = true)
        verify(model.api).getInbox(0)
    }

    @Test
    fun displaySuccess() {
        model.displaySuccess()
        verify(model.viewAccess).closeKeyboard()
    }
}