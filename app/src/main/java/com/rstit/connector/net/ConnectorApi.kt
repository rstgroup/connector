package com.rstit.connector.net

import com.rstit.connector.model.auth.SignInBody
import com.rstit.connector.model.auth.SignInResponse
import com.rstit.connector.model.inbox.InboxEntry
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
const val QUERY_PAGE: String = "page"
const val QUERY_PER_PAGE: String = "per_page"
const val QUERY_SEARCH: String = "name"

interface ConnectorApi {
    @POST("auth/login")
    fun signIn(@Body body: SignInBody): Observable<SignInResponse>

    @POST("auth/change_password")
    fun changePassword(@Body body: ChangePasswordBody): Observable<Response<Void>>

    @GET("messages/inbox")
    fun getInbox(@Query(QUERY_PAGE) page: Int, @Query(QUERY_PER_PAGE) perPage: Int = DEFAULT_PER_PAGE): Observable<Collection<InboxEntry>>

    @POST("messages/to_all")
    fun sendMessageToAll(@Body body: MessageToAllBody): Observable<Response<Void>>

    @GET("users/")
    fun searchUsers(@Query(QUERY_PAGE) page: Int, @Query(QUERY_SEARCH) name: String): Observable<Collection<User>>
}