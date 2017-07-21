package com.rstit.connector.net

import retrofit2.Response
import retrofit2.http.POST
import rx.Observable

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface ConnectorApi {
    @POST("auth/login")
    fun logIn(): Observable<Response<Void>>
}