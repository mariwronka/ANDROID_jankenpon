package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.source.remote.provideJankenponApi
import com.mariwronka.jankenpon.data.source.remote.provideMoshi
import com.mariwronka.jankenpon.data.source.remote.provideOkHttpClient
import com.mariwronka.jankenpon.data.source.remote.provideRetrofit
import org.koin.dsl.module

val NetworkModule = module {
    single { provideMoshi() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideJankenponApi(get()) }
}
