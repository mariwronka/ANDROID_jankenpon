package com.mariwronka.jankenpon.ui.viremodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.domain.entity.PlayerType
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlayersViewModel(private val repository: PlayersRepository) : ViewModel() {

    val youWins: StateFlow<Int> get() = _youWins
    private val _youWins: StateFlow<Int> = repository.getVictoryCount(YOU).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = 0
    )

    val computerWins: StateFlow<Int> get() = _computerWins
    private val _computerWins: StateFlow<Int> = repository.getVictoryCount(COMPUTER).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = 0
    )

    fun onRoundEnd(winner: PlayerType) = viewModelScope.launch {
        repository.incrementVictory(winner)
    }
}
