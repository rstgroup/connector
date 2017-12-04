package com.rstit.connector.ui.auth

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityAuthBinding
import com.rstit.connector.di.auth.AuthModule
import com.rstit.connector.ui.base.BaseActivity
import com.rstit.connector.ui.login.LoginFragment
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class AuthActivity : BaseActivity(), AuthViewAccess {
    @Inject
    lateinit var model: AuthViewModel

    lateinit var binding: ActivityAuthBinding

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.log_in_label)
    }

    override fun showSignIn() {
        supportFragmentManager.
                beginTransaction()
                .replace(R.id.flContainer, LoginFragment())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.plus(AuthModule(this)).inject(this)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.model = model

        setToolbar()

        model.initFragment()
    }
}