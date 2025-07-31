package com.mariwronka.jankenpon.data.source.remote.api

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JankenponApi {
    @GET("rock_paper_scissors/{guess}")
    suspend fun playGame(@Path("guess") guess: String?): JankenponResponse
}
