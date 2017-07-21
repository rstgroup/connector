package com.rstit.connector

import android.app.Application
import android.content.Context
import com.rstit.connector.di.base.AppComponent
import com.rstit.connector.di.base.AppModule
import com.rstit.connector.di.base.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

/**
 * @author Tomasz Trybala
 * @since 2017-07-17
 */
class ConnectorApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }

        appComponent.inject(this)
    }

    companion object {
        fun get(context: Context) = context.applicationContext as ConnectorApplication
    }
}