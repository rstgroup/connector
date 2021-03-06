package com.rstit.connector.ui.login

import android.databinding.ObservableBoolean
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.rstit.binding.ObservableString
import com.rstit.connector.di.base.scope.FragmentScope
import com.rstit.connector.model.auth.SignInBody
import com.rstit.connector.model.auth.SignInResponse
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.settings.AppSettings
import com.rstit.ui.auth.login.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
@FragmentScope
class CustomLoginViewModel @Inject constructor() : LoginViewModel() {
    val error: ObservableString = ObservableString()
    val loading = ObservableBoolean()

    @Inject
    lateinit var viewAccess: CustomLoginViewAccess

    @Inject
    lateinit var api: ConnectorApi

    @Inject
    lateinit var appSettings: AppSettings

    fun validate() =
            if (isInputDataValid) {
                sendRequest()
            } else {
                error.set(if (mLoginError.get() != null) mLoginError.get() else mPasswordError.get())
            }

    fun handleSuccessResponse(response: SignInResponse) {
        appSettings.apiToken = response.token
        response.user?.let {
            appSettings.userAvatar = it.avatar
            appSettings.userEmail = it.email
            appSettings.userName = "${it.name} ${it.lastName}"
            appSettings.userStatus = it.getUserRole().role
        }
        viewAccess.navigateToMain()
    }

    fun handleErrorResponse(error: Throwable?) {
        if (error is HttpException && error.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            viewAccess.displayInvalidDataError()
        } else {
            viewAccess.displayError()
        }
    }

    fun sendRequest() =
            registerDisposable(api.signIn(SignInBody(mLogin.get(), mPassword.get()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        error.set(null)
                        loading.set(true)
                        viewAccess.closeKeyboard()
                    }
                    .doOnTerminate{ loading.set(false) }
                    .subscribe(this::handleSuccessResponse, this::handleErrorResponse))
}
