package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.mapper.JankenponMapper
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.data.source.remote.api.JankenponApi
import com.mariwronka.jankenpon.domain.entity.PlayerType
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PlayersRepositoryImpl(
    private val api: JankenponApi,
    private val mapper: JankenponMapper,
    private val manager: PlayerDataManager,
) : PlayersRepository {

    override fun getVictoryCount(type: PlayerType): Flow<Int> {
        return manager.rawFlow.map { (you, computer) ->
            if (type == YOU) you else computer
        }.distinctUntilChanged()
    }

    override suspend fun playGame(guess: String?) = mapper.map(api.playGame(guess))

    override suspend fun resetVictories() {
        manager.clearVictoryCount(YOU)
        manager.clearVictoryCount(COMPUTER)
    }

    override suspend fun incrementVictory(type: PlayerType) {
        type.takeIf { it == YOU }?.let { manager.incrementYouWins() } ?: manager.incrementComputerWins()
    }

    override suspend fun clearVictoryCount(type: PlayerType) {
        manager.clearVictoryCount(type)
    }
}
