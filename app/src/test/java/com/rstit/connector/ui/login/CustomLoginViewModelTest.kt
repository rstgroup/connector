package com.rstit.connector.ui.login

import com.rstit.connector.BaseTest
import com.rstit.connector.di.base.DaggerTestAppComponent
import com.rstit.connector.di.login.TestCustomLoginModule
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException
import javax.inject.Inject


/**
 * @author Tomasz Trybala
 * *
 * @since 2017-07-24
 */
class CustomLoginViewModelTest : BaseTest() {
    @Inject
    lateinit var model: CustomLoginViewModel

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .build()
                .plus(TestCustomLoginModule())
                .inject(this)
    }

    @Test
    fun checkInitialState() {
        assertThat(model.error.get(), nullValue())
        assertThat(model.loading.get(), `is`(false))
    }

    @Test
    fun handleErrorResponse() {
        model.handleErrorResponse(IOException())
        Mockito.verify(model.viewAccess).displayError()
    }

    @Test
    fun handleSuccessResponse() {
        model.handleSuccessResponse(signInResponse)
        Mockito.verify(model.viewAccess).navigateToMain()
    }

    @Test
    fun sendRequest() {
        model.mLogin.set("login")
        model.mPassword.set("pass")

        `when`(model.api.signIn(any())).thenReturn(Observable.just(signInResponse))
        model.sendRequest()
        Mockito.verify(model.api).signIn(any())
    }
}