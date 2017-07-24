package com.rstit.connector

import com.rstit.connector.model.auth.SignInResponse
import com.rstit.connector.model.user.User
import org.junit.Rule
import org.mockito.Mockito

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
open class BaseTest {
    @Suppress("unused")
    @get:Rule val rxJavaRule: RxJavaTestRule = RxJavaTestRule()

    val user: User =
            User(1, "name", "lastName", "email@email.com",
                    "avatar.jpg", "developer")

    val signInResponse: SignInResponse =
            SignInResponse("token", user)

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()

    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}