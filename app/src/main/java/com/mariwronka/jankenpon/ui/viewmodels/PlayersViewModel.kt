package com.mariwronka.jankenpon.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.DEFEAT
import com.mariwronka.jankenpon.domain.entity.WinnerType.DRAW
import com.mariwronka.jankenpon.domain.entity.WinnerType.VICTORY
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.mariwronka.jankenpon.ui.common.base.BaseViewModel
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

    private val _jankenponResult = MutableSharedFlow<JankenponResult>()
    val jankenponResult: SharedFlow<JankenponResult> get() = _jankenponResult

    private val _resetEvent = MutableSharedFlow<Unit>()
    val resetEvent: SharedFlow<Unit> get() = _resetEvent

    fun playGame(guess: String) {
        launchDataLoad {
            repository.playGame(guess).let { result ->
                _jankenponResult.emit(result)
                result.winner.fromWinnerType()?.let { winner ->
                    when (winner) {
                        VICTORY -> repository.incrementVictory(YOU)
                        DEFEAT -> repository.incrementVictory(COMPUTER)
                        DRAW -> Unit
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
