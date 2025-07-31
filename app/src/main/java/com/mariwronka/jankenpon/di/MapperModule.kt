package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.mapper.JankenponMapperImpl
import org.koin.dsl.module

val MapperModule = module {
    single<JankenponMapper> { JankenponMapperImpl() }
}
