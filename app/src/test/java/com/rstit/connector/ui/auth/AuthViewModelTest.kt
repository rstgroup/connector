package com.rstit.connector.ui.auth

import com.rstit.connector.di.auth.TestAuthModule
import com.rstit.connector.di.base.DaggerTestAppComponent
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * *
 * @since 2017-07-24
 */
class AuthViewModelTest {
    @Inject
    lateinit var model: AuthViewModel

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .build()
                .plus(TestAuthModule())
                .inject(this)
    }

    @Test
    fun initFragmentTest() {
        model.initFragment()
        Mockito.verify(model.viewAccess).showSignIn()
    }

}