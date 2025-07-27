package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.mapper.JankenponMapperImpl
import com.mariwronka.jankenpon.data.repository.JankenponRepositoryImpl
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.domain.repository.JankenponRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

inline fun <reified T : Any> mockRetrofit(): T = mockk(relaxed = true)

fun provideTestApiModule(mockWebServer: MockWebServer) = module {

    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    single {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<JankenponApi> { get<Retrofit>().create(JankenponApi::class.java) }

    single<JankenponMapper> { JankenponMapperImpl() }

    single<JankenponRepository> { JankenponRepositoryImpl(get(), get()) }
}
