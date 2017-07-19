package com.rstit.connector.net

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.POST

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface ConnectorApi {
    @POST("auth/login")
    fun logIn(): Observable<Response<Void>>
}