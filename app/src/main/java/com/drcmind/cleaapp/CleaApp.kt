package com.drcmind.cleaapp

import android.app.Application
import com.drcmind.cleaapp.di.appModule
import com.drcmind.cleaapp.di.networkModule
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
                networkModule,
                appModule
            )
        }
    }
}
