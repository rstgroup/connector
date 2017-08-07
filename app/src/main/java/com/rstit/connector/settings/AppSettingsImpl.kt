package com.rstit.connector.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
const val SETTINGS_NAME = "app_settings"
const val SETTINGS_TOKEN = "api_token"
const val SETTINGS_USER_NAME = "user_name"
const val SETTINGS_USER_EMAIL = "user_email"
const val SETTINGS_USER_AVATAR = "user_avatar"
const val SETTINGS_USER_STATUS = "user_status"


class AppSettingsImpl(context: Context) : AppSettings {
    private var context: Context = context.applicationContext

    @SuppressLint("CommitPrefEdits")
    private fun clearAll(){
        val settings = context.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE)
        settings.edit().apply {
            clear()
            apply()
        }
    }

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

    override fun logOut() = clearAll()

    override var apiToken: String?
        get() = loadFromSettings(SETTINGS_TOKEN)
        set(value) {
            value?.let { saveToSettings(SETTINGS_TOKEN, it) }
        }

    override var userName: String?
        get() = loadFromSettings(SETTINGS_USER_NAME)
        set(value) {
            value?.let { saveToSettings(SETTINGS_USER_NAME, it) }
        }

    override var userEmail: String?
        get() = loadFromSettings(SETTINGS_USER_EMAIL)
        set(value) {
            value?.let { saveToSettings(SETTINGS_USER_EMAIL, it) }
        }

    override var userAvatar: String?
        get() = loadFromSettings(SETTINGS_USER_AVATAR)
        set(value) {
            value?.let { saveToSettings(SETTINGS_USER_AVATAR, it) }
        }

    override var userStatus: String?
        get() = loadFromSettings(SETTINGS_USER_STATUS)
        set(value) {
            value?.let { saveToSettings(SETTINGS_USER_STATUS, it) }
        }
}