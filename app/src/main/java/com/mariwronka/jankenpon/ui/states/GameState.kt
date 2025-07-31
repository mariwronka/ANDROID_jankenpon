package com.mariwronka.jankenpon.ui.states

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.WinnerType

data class GameState(
    val result: JankenponResult,
    val playerPoints: Int,
    val opponentPoints: Int,
    val roundWinner: WinnerType? = null,
    val finalRoundWinner: WinnerType? = null,
)
