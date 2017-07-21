package com.rstit.connector.ui.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityMainBinding
import com.rstit.connector.di.main.MainModule
import com.rstit.connector.ui.auth.AuthActivity
import com.rstit.connector.ui.base.BaseActivity
import com.rstit.connector.ui.base.MultiViewAdapter
import com.rstit.connector.ui.password.ResetPasswordActivity
import com.rstit.connector.ui.search.UserSearchActivity
import javax.inject.Inject


/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class MainActivity : BaseActivity(), MainViewAccess {
    @Inject
    lateinit var model: MainViewModel

    lateinit var binding: ActivityMainBinding

    override val adapter: MultiViewAdapter by lazy {
        MultiViewAdapter.Builder(model.models)
                .register(R.layout.row_main, MainRowViewModel::class.java, this::setChatListener)
                .build()
    }

    private fun setChatListener(model: MainRowViewModel, binding: ViewDataBinding) =
            binding.root.setOnClickListener({ navigateToChat(model) })

    private fun navigateToChat(model: MainRowViewModel) {
        //todo navigate to chat
    }

    private fun navigateToChangePassword() = startActivity(Intent(this, ResetPasswordActivity::class.java))

    private fun signOut() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun displaySignOutDialog() =
            AlertDialog.Builder(this)
                    .setTitle(R.string.sign_out_label)
                    .setMessage(R.string.sign_out_message)
                    .setNegativeButton(R.string.sign_out_cancel, { _, _ -> /*no-op*/ })
                    .setPositiveButton(R.string.sign_out_yes, { _, _ -> signOut() })
                    .create()
                    .show()


    private fun setToolbar() = setSupportActionBar(binding.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConnectorApplication.get(this)
                .appComponent
                .plus(MainModule(this))
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = model
        binding.viewAccess = this

        setToolbar()

        model.loadData()
    }

    override fun selectPerson() {
        binding.fabMenu.collapse()
        startActivity(Intent(this, UserSearchActivity::class.java))
    }

    override fun writeToAll() {
        binding.fabMenu.collapse()
        model.showMessage()
    }

    override fun closeKeyboard() = hideKeyboard()

    override fun displaySuccessSnackbar() {
        model.hideMessage()
        showSnackbar(binding.mainCoordinator, R.string.message_to_all_success, Snackbar.LENGTH_LONG)
    }

    override fun displayErrorMessage() = showSnackbar(binding.flMessage, R.string.message_to_all_error)

    override fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                R.id.action_sign_out -> {
                    displaySignOutDialog()
                    true
                }
                R.id.action_change_password -> {
                    navigateToChangePassword()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}