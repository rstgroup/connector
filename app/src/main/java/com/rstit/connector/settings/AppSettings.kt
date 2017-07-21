package com.rstit.connector.settings

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface AppSettings {
    var apiToken: String?

    var userName: String?

    var userEmail: String?

    var userAvatar: String?

    fun isUserLogged(): Boolean

    fun logOut()
}