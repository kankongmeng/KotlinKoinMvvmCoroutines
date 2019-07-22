package com.brian.kotlinkoinmvvmcoroutines

import android.app.Application
import com.brian.kotlinkoinmvvmcoroutines.module.appModules
import com.brian.kotlinkoinmvvmcoroutines.module.provideNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Add Koin modules to our application
        startKoin {
            androidContext(this@App)
            modules(listOf(provideNetworkModule(BASE_URL), appModules))
        }
    }

    companion object {
        private const val BASE_URL = "http://5c611e591325a20014976373.mockapi.io/api/v1/"
    }
}
