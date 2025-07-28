package com.mariwronka.jankenpon.ui.viremodels

import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.domain.entity.WinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.DEFEAT
import com.mariwronka.jankenpon.domain.entity.WinnerType.VICTORY
import com.mariwronka.jankenpon.domain.repository.JankenponRepository
import com.mariwronka.jankenpon.ui.common.BaseViewModel
import com.mariwronka.jankenpon.ui.states.GameState
import kotlinx.coroutines.CoroutineDispatcher

class JankenponViewModel(
    private val repository: JankenponRepository,
    private val playerDataManager: PlayerDataManager,
    ioDispatcher: CoroutineDispatcher,
) : BaseViewModel<GameState>(ioDispatcher) {

    private companion object {
        private const val MAX_ROUNDS = 3
    }

    private var roundsPlayed = 1
    private var playerPoints = 0
    private var opponentPoints = 0

    private var finalRoundsWinner: WinnerType? = null

    fun playGame(guess: String) {
        launchDataLoad {
            val result = repository.playGame(guess)

            when (result.winner.fromWinnerType()) {
                VICTORY -> playerPoints += 1
                DEFEAT -> opponentPoints += 1
                else -> Unit
            }

            checkRoundsPlayed()

            GameState(
                result = result,
                playerPoints = playerPoints,
                opponentPoints = opponentPoints,
                roundWinner = result.winner.fromWinnerType()
            )
        }
    }

    private fun checkRoundsPlayed() {
        roundsPlayed += 1
        if (roundsPlayed > MAX_ROUNDS) {
            finalRoundsWinner = if (opponentPoints > playerPoints) DEFEAT else VICTORY
            initRoundsPlayed()
        }
    }

    fun initRoundsPlayed() {
        roundsPlayed = 1
        playerPoints = 0
        opponentPoints = 0
    }

    fun savePlayerPoints(playerName: String) {
        playerDataManager.updatePlayerPoints(playerName)
    }
}
