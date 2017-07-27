package com.rstit.connector.ui.password

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityResetPasswordBinding
import com.rstit.connector.di.password.ResetPasswordModule
import com.rstit.connector.ui.base.BaseActivity
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
class ResetPasswordActivity : BaseActivity(), ResetPasswordViewAccess {
    lateinit var binding: ActivityResetPasswordBinding

    @Inject
    lateinit var model: ResetPasswordViewModel

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.change_password_label)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConnectorApplication.get(this)
                .appComponent
                .plus(ResetPasswordModule(this))
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        binding.model = model

        setToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    backToMain()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }


    override val getNewPasswordError: String = getString(R.string.reset_password_new_invalid)

    override val getConfirmPasswordError: String = getString(R.string.reset_password_confirm_invalid)

    override val getEmptyOldPasswordError: String = getString(R.string.reset_password_old_invalid_empty)

    override fun closeKeyboard() = hideKeyboard()

    override fun displayOldPasswordError() = showSnackbar(binding.root, R.string.reset_password_old_invalid)

    override fun backToMain() = finish()

    override fun displaySuccess() =
            AlertDialog.Builder(this)
                    .setTitle(R.string.reset_password_success_title)
                    .setMessage(R.string.reset_password_success_message)
                    .setPositiveButton(R.string.reset_password_success_button, { _, _ -> backToMain() })
                    .setCancelable(false)
                    .create()
                    .show()
}