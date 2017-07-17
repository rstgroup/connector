package com.rstit.connector

import android.app.Application
import com.rstit.connector.di.base.AppComponent
import com.rstit.connector.di.base.AppModule
import com.rstit.connector.di.base.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class ConnectorApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }

        component.inject(this)
    }
}