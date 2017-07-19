package com.rstit.connector.settings

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
interface AppSettings {
    var apiToken: String?

    fun isUserLogged(): Boolean
}