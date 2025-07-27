package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.mapper.JankenponMapperImpl
import com.mariwronka.jankenpon.data.mapper.PlayerNameMapper
import com.mariwronka.jankenpon.data.mapper.PlayerNameMapperImpl
import org.koin.dsl.module

val MapperModule = module {
    single<PlayerNameMapper> { PlayerNameMapperImpl() }
    single<JankenponMapper> { JankenponMapperImpl() }
}
