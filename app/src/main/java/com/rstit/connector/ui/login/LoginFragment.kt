package com.rstit.connector.ui.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rstit.connector.R
import com.rstit.connector.databinding.FragmentLoginBinding
import com.rstit.connector.di.login.LoginModule
import com.rstit.connector.ui.base.BaseFragment
import com.rstit.connector.ui.main.MainActivity
import javax.inject.Inject


/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class LoginFragment : BaseFragment(), CustomLoginViewAccess {
    @Inject
    lateinit var model: CustomLoginViewModel

    lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.plus(LoginModule(this)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, null, false)
        binding.model = model

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        model.clearDisposables()
    }

    override fun getLoginErrorMessage(): String = getString(R.string.error_login)

    override fun getPasswordErrorMessage(): String = getString(R.string.error_password)

    override fun navigateToMain() {
        activity?.let {
            startActivity(Intent(it, MainActivity::class.java))
            it.finish()
        }
    }

    override fun displayError() = showSnackbar(binding.root, R.string.error_sign_in)

    override fun closeKeyboard() = hideKeyboard()
}