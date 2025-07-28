package com.mariwronka.jankenpon.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DispatcherModule = module {
    single<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }
}
