package com.rstit.connector.net

import com.rstit.connector.model.auth.SignInBody
import com.rstit.connector.model.auth.SignInResponse
import com.rstit.connector.model.inbox.InboxResponse
import com.rstit.connector.model.password.ChangePasswordBody
import com.rstit.connector.model.password.MessageToAllBody
import com.rstit.connector.model.user.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
const val DEFAULT_PER_PAGE: Int = 25
const val FIRST_PAGE: Int = 0
const val QUERY_OFFSET: String = "offset"
const val QUERY_SIZE: String = "size"
const val QUERY_SEARCH: String = "name"

interface ConnectorApi {
    @POST("auth/login")
    fun signIn(@Body body: SignInBody): Observable<SignInResponse>

    @POST("auth/change_password")
    fun changePassword(@Body body: ChangePasswordBody): Observable<Response<Void>>

    @GET("messages/inbox")
    fun getInbox(@Query(QUERY_OFFSET) offset: Int, @Query(QUERY_SIZE) size: Int = DEFAULT_PER_PAGE): Observable<InboxResponse>

    @POST("messages/to_all")
    fun sendMessageToAll(@Body body: MessageToAllBody): Observable<Response<Void>>

    @GET("users/")
    fun searchUsers(@Query(QUERY_OFFSET) page: Int = FIRST_PAGE, @Query(QUERY_SEARCH) name: String): Observable<Collection<User>>
}