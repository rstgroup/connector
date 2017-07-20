package com.rstit.connector.ui.password

import com.rstit.binding.ObservableString
import com.rstit.di.names.NonEmptyValidatorName
import com.rstit.di.names.PasswordValidatorName
import com.rstit.ui.base.model.BaseViewModel
import com.rstit.validation.Validator
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
class ResetPasswordViewModel @Inject constructor() : BaseViewModel() {
    val oldPassword = ObservableString()
    val newPassword = ObservableString()
    val confirmPassword = ObservableString()
    val error = ObservableString()

    @Inject
    lateinit var viewAccess: ResetPasswordViewAccess

    @field:[Inject PasswordValidatorName]
    lateinit var passwordValidator: Validator

    @field:[Inject NonEmptyValidatorName]
    lateinit var nonEmptyValidator: Validator

    fun sendRequest() {
        viewAccess.closeKeyboard()
        //todo implement
    }

    fun validate() {
        error.set(null)

        if (!nonEmptyValidator.isValid(oldPassword.get())) {
            error.set(viewAccess.getEmptyOldPasswordError())
            return
        }

        if (!passwordValidator.isValid(newPassword.get())) {
            error.set(viewAccess.getNewPasswordError())
            return
        }

        if (newPassword.get() != confirmPassword.get()) {
            error.set(viewAccess.getConfirmPasswordError())
            return
        }

        sendRequest()
    }
}