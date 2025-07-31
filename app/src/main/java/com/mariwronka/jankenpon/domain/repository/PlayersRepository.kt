package com.mariwronka.jankenpon.domain.repository

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.PlayerType
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    suspend fun incrementVictory(type: PlayerType)
    suspend fun clearVictoryCount(type: PlayerType)
    suspend fun playGame(guess: String?): JankenponResult
    suspend fun resetVictories()
    fun getVictoryCount(type: PlayerType): Flow<Int>
}
