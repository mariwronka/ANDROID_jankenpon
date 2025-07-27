package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.domain.repository.JankenponRepository

class JankenponRepositoryImpl(
    private val api: JankenponApi,
    private val mapper: JankenponMapper,
) : JankenponRepository {
    override suspend fun playGame(guess: String?) = mapper.map(api.playGame(guess))
}
