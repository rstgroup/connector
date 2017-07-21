package com.rstit.connector.ui.base

import android.support.v7.app.AppCompatActivity
import com.rstit.connector.ConnectorApplication

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
open class BaseActivity : AppCompatActivity() {
    val appComponent by lazy {
        ConnectorApplication.get(applicationContext).appComponent
    }
}
