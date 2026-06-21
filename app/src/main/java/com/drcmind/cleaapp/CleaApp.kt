package com.drcmind.cleaapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CleaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CleaApp)
            modules(
                com.drcmind.cleaapp.di.networkModule,
                com.drcmind.cleaapp.di.dataModule,
                com.drcmind.cleaapp.di.viewModelModule
            )
        }
    }
}
