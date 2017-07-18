package com.rstit.connector.settings

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface AppSettings {
    fun loadToken(): String?

    fun saveToken(token: String)

    fun isUserLogged(): Boolean

    fun logOut()
}