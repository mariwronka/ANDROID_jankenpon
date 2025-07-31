package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.repository.PlayersRepositoryImpl
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<PlayersRepository> {
        PlayersRepositoryImpl(
            api = get(),
            mapper = get(),
            manager = get(),
        )
    }
}
