package com.rstit.connector.ui.password

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
interface ResetPasswordViewAccess {
    fun closeKeyboard()

    fun displayOldPasswordError()

    fun displayDefaultError()

    val getEmptyOldPasswordError: String

    val getNewPasswordError: String

    val getConfirmPasswordError: String

    fun backToMain()

    fun displaySuccess()
}