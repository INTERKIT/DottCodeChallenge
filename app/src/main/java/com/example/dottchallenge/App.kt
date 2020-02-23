package com.example.dottchallenge

import android.app.Application
import com.example.dottchallenge.common.di.NetworkModule
import com.example.dottchallenge.map.di.MapsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            val modules = listOf(
                NetworkModule.create(),
                MapsModule.create()
            )

            modules(modules)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}