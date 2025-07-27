package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.mapper.PlayerNameMapper
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.domain.entity.PlayerName
import com.mariwronka.jankenpon.domain.repository.PlayersRepository

class PlayersRepositoryImpl(
    private val jankenponApi: JankenponApi,
    private val mapper: PlayerNameMapper,
) : PlayersRepository {
    override suspend fun getPlayerName(): PlayerName {
        return mapper.map(jankenponApi.getPlayerName())
    }
}
