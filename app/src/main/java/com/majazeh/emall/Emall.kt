package com.majazeh.emall

import android.app.Application
import com.ali74.libkot.utils.AppTheme
import com.majazeh.emall.di.*
import com.majazeh.emall.utils.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("Unused")
class Emall : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)

        AppTheme.setAppFont(this, "DroidKufi_Regular.ttf")

        startKoin {
            createEagerInstances()
            androidLogger(Level.DEBUG)
            androidContext(this@Emall)
            modules(listOf(apiModule, viewModelModule, repositoryModule, roomModule, networkModule))
        }
    }

}