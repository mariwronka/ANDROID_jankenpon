package com.mariwronka.jankenpon.domain.repository

import com.mariwronka.jankenpon.domain.entity.PlayerName

interface PlayersRepository {
    suspend fun getPlayerName(): PlayerName
}
