package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.repository.JankenponRepository

class JankenponRepositoryImpl(private val jankenponApi: JankenponApi) : JankenponRepository {
    override suspend fun playGame(guess: String?): JankenponResult {
        return JankenponMapper.mapResponseToModel(jankenponApi.playGame(guess))
    }
}
