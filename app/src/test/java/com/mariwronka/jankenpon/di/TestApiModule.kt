package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.mapper.JankenponMapperImpl
import com.mariwronka.jankenpon.data.repository.PlayersRepositoryImpl
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideTestApiModule(mockWebServer: MockWebServer) = module {
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    single<JankenponApi> {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(JankenponApi::class.java)
    }

    single<JankenponMapper> { JankenponMapperImpl() }

    single { mockk<PlayerDataManager>(relaxed = true) }

    single<PlayersRepository> { PlayersRepositoryImpl(get(), get(), get()) }
}
