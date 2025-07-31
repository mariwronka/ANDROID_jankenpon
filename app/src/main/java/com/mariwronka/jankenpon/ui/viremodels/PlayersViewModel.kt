package com.mariwronka.jankenpon.ui.viremodels

import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.entity.WinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.DEFEAT
import com.mariwronka.jankenpon.domain.entity.WinnerType.DRAW
import com.mariwronka.jankenpon.domain.entity.WinnerType.VICTORY
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.mariwronka.jankenpon.ui.common.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PlayersViewModel(private val repository: PlayersRepository) : BaseViewModel<Unit>() {

    val youWins: StateFlow<Int> get() = _youWins
    private val _youWins: StateFlow<Int> = repository.getVictoryCount(YOU).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = 0,
    )

    val computerWins: StateFlow<Int> get() = _computerWins
    private val _computerWins: StateFlow<Int> = repository.getVictoryCount(COMPUTER).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = 0,
    )

    private val _winnerType = MutableSharedFlow<WinnerType>()
    val winnerType: SharedFlow<WinnerType> get() = _winnerType

    private val _resetEvent = MutableSharedFlow<Unit>()
    val resetEvent: SharedFlow<Unit> get() = _resetEvent

    fun playGame(guess: String) {
        launchDataLoad {
            repository.playGame(guess).let { result ->
                result.winner.fromWinnerType()?.let { winner ->
                    _winnerType.emit(winner)

                    when (winner) {
                        VICTORY -> {
                            val updated = repository.incrementVictory(YOU)
                            println(">> YOU VICTORY -> total: $updated")
                        }
                        DEFEAT -> {
                            val updated = repository.incrementVictory(COMPUTER)
                            println(">> COMPUTER VICTORY -> total: $updated")
                        }
                        DRAW -> println(">> Empate")
                    }
                }
            }
        }
    }

    fun resetGame() = launchDataLoad {
        repository.clearVictoryCount(YOU)
        repository.clearVictoryCount(COMPUTER)
    }
}
