package com.mariwronka.jankenpon.domain.repository

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult

interface JankenponRepository {
    suspend fun playGame(guess: String?): JankenponResult
}
