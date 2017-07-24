package com.rstit.connector

import com.rstit.connector.model.auth.SignInResponse
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.model.inbox.InboxResponse
import com.rstit.connector.model.inbox.Message
import com.rstit.connector.model.password.ChangePasswordBody
import com.rstit.connector.model.user.User
import okhttp3.ResponseBody
import org.junit.Rule
import org.mockito.Mockito
import retrofit2.Response
import java.util.*

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

    val message: Message =
            Message(1, "content", "read", Date(), false)

    val signInResponse: SignInResponse =
            SignInResponse("token", user)

    val inboxEntry: InboxEntry =
            InboxEntry(user, message)

    val inboxResponse: InboxResponse =
            InboxResponse(listOf(inboxEntry), true)

    val changePasswordBody: ChangePasswordBody =
            ChangePasswordBody("oldPass", "newPass")

    fun response(success: Boolean): Response<Void> =
            if (success) {
                Response.success(null)
            } else {
                Response.error<Void>(400, Mockito.mock(ResponseBody::class.java))
            }

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}