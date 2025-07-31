package com.mariwronka.jankenpon.data.source.remote

import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor().apply { level = BODY })
    .build()

fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.toys/api/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .build()

fun provideJankenponApi(retrofit: Retrofit): JankenponApi = retrofit.create(JankenponApi::class.java)
