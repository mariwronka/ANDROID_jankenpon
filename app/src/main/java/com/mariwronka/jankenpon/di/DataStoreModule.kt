package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataStoreModule = module {
    single { PlayerDataManager(androidContext()) }
}
