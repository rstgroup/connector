package com.rstit.connector.ui.password

import android.databinding.ObservableBoolean
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.model.password.ChangePasswordBody
import com.rstit.connector.model.password.ChangePasswordResponse
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.settings.AppSettings
import com.rstit.di.names.NonEmptyValidatorName
import com.rstit.di.names.PasswordValidatorName
import com.rstit.ui.base.model.BaseViewModel
import com.rstit.validation.Validator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
@ActivityScope
class ResetPasswordViewModel @Inject constructor() : BaseViewModel() {
    val oldPassword = ObservableString()
    val newPassword = ObservableString()
    val confirmPassword = ObservableString()
    val error = ObservableString()
    val loading = ObservableBoolean()

    @Inject
    lateinit var viewAccess: ResetPasswordViewAccess

    @field:[Inject PasswordValidatorName]
    lateinit var passwordValidator: Validator

    @field:[Inject NonEmptyValidatorName]
    lateinit var nonEmptyValidator: Validator

    @Inject
    lateinit var api: ConnectorApi

    @Inject
    lateinit var appSettings: AppSettings

    fun sendRequest() =
            registerDisposable(api.changePassword(ChangePasswordBody(oldPassword.get(), newPassword.get()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        error.set(null)
                        loading.set(true)
                        viewAccess.closeKeyboard()
                    }
                    .doOnTerminate { loading.set(false) }
                    .subscribe(this::handleResponse, this::handleErrorResponse))

    fun handleResponse(response: ChangePasswordResponse) {
        appSettings.apiToken = response.token
        viewAccess.displaySuccess()
    }

    fun handleErrorResponse(error: Throwable?) {
        if (error is HttpException && error.code() == HttpURLConnection.HTTP_CONFLICT) {
            viewAccess.displayOldPasswordError()
        } else {
            viewAccess.displayDefaultError()
        }
    }

    fun validate() {
        error.set(null)

        if (!nonEmptyValidator.isValid(oldPassword.get())) {
            error.set(viewAccess.getEmptyOldPasswordError)
            return
        }

        if (!passwordValidator.isValid(newPassword.get())) {
            error.set(viewAccess.getNewPasswordError)
            return
        }

        if (newPassword.get() != confirmPassword.get()) {
            error.set(viewAccess.getConfirmPasswordError)
            return
        }

        sendRequest()
    }
}