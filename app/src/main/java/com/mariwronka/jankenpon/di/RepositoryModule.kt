package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.repository.JankenponRepositoryImpl
import com.mariwronka.jankenpon.data.repository.PlayersRepositoryImpl
import com.mariwronka.jankenpon.domain.repository.JankenponRepository
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<JankenponRepository> { JankenponRepositoryImpl(get(), get()) }
    single<PlayersRepository> { PlayersRepositoryImpl(get(), get()) }
}
