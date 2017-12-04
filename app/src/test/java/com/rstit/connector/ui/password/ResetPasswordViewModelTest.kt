package com.rstit.connector.ui.password

import com.rstit.connector.BaseTest
import com.rstit.connector.di.base.DaggerTestAppComponent
import com.rstit.connector.di.password.TestResetPasswordModule
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * *
 * @since 2017-07-24
 */
class ResetPasswordViewModelTest : BaseTest() {
    @Inject
    lateinit var model: ResetPasswordViewModel

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .build()
                .plus(TestResetPasswordModule())
                .inject(this)
    }

    @Test
    fun checkInitialState() {
        assertThat(model.oldPassword.get(), nullValue())
        assertThat(model.newPassword.get(), nullValue())
        assertThat(model.confirmPassword.get(), nullValue())
        assertThat(model.error.get(), nullValue())
        assertThat(model.loading.get(), `is`(false))
    }

    @Test
    fun sendRequest() {
        model.oldPassword.set("oldPass")
        model.newPassword.set("newPass")
        Mockito.`when`(model.api.changePassword(any())).thenReturn(Observable.just(changePasswordResponse))
        model.sendRequest()
        Mockito.verify(model.api).changePassword(any())
    }

    @Test
    fun handleResponse() {
        model.handleResponse(changePasswordResponse)
        Mockito.verify(model.appSettings).apiToken = changePasswordResponse.token
        Mockito.verify(model.viewAccess).displaySuccess()
    }

    @Test
    fun handleErrorResponse_Default() {
        model.handleErrorResponse(IOException())
        Mockito.verify(model.viewAccess).displayDefaultError()
    }

    @Test
    fun validate_emptyOld() {
        model.oldPassword.set("")
        Mockito.`when`(model.viewAccess.getEmptyOldPasswordError).thenReturn("empty")
        model.validate()
        assertThat(model.error.get(), notNullValue())
    }

    @Test
    fun validate_incorrectNew() {
        model.oldPassword.set("oldPass")
        model.newPassword.set("")
        Mockito.`when`(model.viewAccess.getNewPasswordError).thenReturn("empty")
        model.validate()
        assertThat(model.error.get(), notNullValue())
    }

    @Test
    fun validate_incorrectConfirm() {
        model.oldPassword.set("oldPass")
        model.newPassword.set("newPass")
        model.confirmPassword.set("newPass1")
        Mockito.`when`(model.viewAccess.getConfirmPasswordError).thenReturn("empty")
        model.validate()
        assertThat(model.error.get(), notNullValue())
    }

    @Test
    fun validate_correct() {
        model.oldPassword.set("oldPass")
        model.newPassword.set("newPass")
        model.confirmPassword.set("newPass")

        Mockito.`when`(model.api.changePassword(any())).thenReturn(Observable.just(changePasswordResponse))
        model.validate()
        assertThat(model.error.get(), nullValue())
        Mockito.verify(model.api).changePassword(any())
    }
}