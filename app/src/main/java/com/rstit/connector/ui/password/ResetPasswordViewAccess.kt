package com.rstit.connector.ui.password

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
interface ResetPasswordViewAccess {
    fun closeKeyboard()

    fun displayOldPasswordError()

    fun getEmptyOldPasswordError(): String

    fun getNewPasswordError(): String

    fun getConfirmPasswordError(): String

    fun backToMain()

    fun displaySuccess()
}