package com.rstit.connector.settings

import android.app.Activity
import android.content.Context

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
const val SETTINGS_NAME = "app_settings"
const val SETTINGS_TOKEN = "api_token"


class AppSettingsImpl(context: Context) : AppSettings {
    private var context: Context = context.applicationContext

    private fun clearSetting(key: String) {
        val settings = context.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        val preferencesEditor = settings.edit()

        preferencesEditor.remove(key)
        preferencesEditor.apply()
    }

    private fun saveToSettings(key: String, value: String) {
        val settings = context.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        val preferencesEditor = settings.edit()

        preferencesEditor.putString(key, value)
        preferencesEditor.apply()
    }

    private fun loadFromSettings(key: String): String? =
            context.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE).getString(key, null)

    override fun isUserLogged(): Boolean = loadFromSettings(SETTINGS_TOKEN) != null

    override fun logOut() = clearSetting(SETTINGS_TOKEN)

    override var apiToken: String?
        get() = loadFromSettings(SETTINGS_TOKEN)
        set(value) = value.let { saveToSettings(SETTINGS_TOKEN, it!!) }
}