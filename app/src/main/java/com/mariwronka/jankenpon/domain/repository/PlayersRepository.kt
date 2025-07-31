package com.mariwronka.jankenpon.domain.repository

import com.mariwronka.jankenpon.domain.entity.PlayerType
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    suspend fun incrementVictory(type: PlayerType): Int
    fun getVictoryCount(type: PlayerType): Flow<Int>
}
