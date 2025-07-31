package com.mariwronka.jankenpon

import android.app.Application
import com.mariwronka.jankenpon.di.AppModule
import com.mariwronka.jankenpon.di.DataStoreModule
import com.mariwronka.jankenpon.di.DispatcherModule
import com.mariwronka.jankenpon.di.MapperModule
import com.mariwronka.jankenpon.di.NetworkModule
import com.mariwronka.jankenpon.di.RepositoryModule
import com.mariwronka.jankenpon.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JankenponApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JankenponApp)
            modules(
                AppModule,
                DispatcherModule,
                NetworkModule,
                RepositoryModule,
                ViewModelModule,
                MapperModule,
                DataStoreModule,
            )
        }
    }
}
