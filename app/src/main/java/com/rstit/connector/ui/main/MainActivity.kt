package com.rstit.connector.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityMainBinding
import com.rstit.connector.di.main.MainModule
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
                .register(R.layout.row_main, MainRowViewModel::class.java)
                .build()
    }

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

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}