package com.mariwronka.jankenpon.data.source.remote.api

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.data.source.remote.entity.PlayerNameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JankenponApi {
    @GET("medieval_name")
    suspend fun getPlayerName(
        @Query("num") num: Int = 1,
        @Query("group") group: String = "all",
        @Query("gender") gender: String = "male",
        @Query("surname") includeSurname: Boolean = false,
    ): PlayerNameResponse

    @GET("rock_paper_scissors/{guess}")
    suspend fun playGame(
        @Path("guess") guess: String?,
    ): JankenponResponse
}
