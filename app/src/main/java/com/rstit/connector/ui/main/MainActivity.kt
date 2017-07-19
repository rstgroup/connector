package com.rstit.connector.ui.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
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

    private fun signOut() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun displaySignOutDialog() =
            AlertDialog.Builder(this)
                    .setTitle(R.string.sign_out_label)
                    .setMessage(R.string.sign_out_message)
                    .setNegativeButton(R.string.sign_out_cancel, { dialog, i -> /*no-op*/ })
                    .setPositiveButton(R.string.sign_out_yes, { dialog, i -> signOut() })
                    .create()
                    .show()


    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.main_label)
    }

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

    override fun notifyDataSetChanged() =
            adapter.notifyDataSetChanged()

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
                else -> super.onOptionsItemSelected(item)
            }
}