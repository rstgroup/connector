package com.rstit.connector.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.databinding.FragmentLoginBinding
import com.rstit.connector.di.login.LoginModule
import com.rstit.ui.auth.login.LoginViewAccess
import com.rstit.ui.auth.login.LoginViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class LoginFragment : Fragment(), LoginViewAccess {
    @Inject
    lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConnectorApplication.get(activity)
                .appComponent
                .plus(LoginModule(this))
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, null, false)
        binding.model = model

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        model.clearDisposables()
    }

    override fun getLoginErrorMessage(): String = getString(R.string.error_login)

    override fun getPasswordErrorMessage(): String = getString(R.string.error_password)
}