package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.domain.entity.PlayerType
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayersRepositoryImpl(
    private val manager: PlayerDataManager,
) : PlayersRepository {

    override fun getVictoryCount(type: PlayerType): Flow<Int> =
        manager.rawFlow.map { (you, comp) ->
            if (type == PlayerType.YOU) you
            else comp
        }

    override suspend fun incrementVictory(type: PlayerType): Int {
        return if (type == PlayerType.YOU) manager.incrementYouWins() else manager.incrementComputerWins()
    }
}
