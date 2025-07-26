package com.mariwronka.jankenpon

import android.app.Application
import com.mariwronka.jankenpon.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JankenponApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JankenponApp)
            modules(appModule)
        }
    }
}