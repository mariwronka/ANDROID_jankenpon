package com.mariwronka.jankenpon.ui.viremodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.WinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.domain.repository.JankenponRepository
import kotlinx.coroutines.launch

class JankenponViewModel(
    private val repository: JankenponRepository,
    private val playerDataManager: PlayerDataManager,
) : ViewModel() {

    private val _roundsPlayed = MutableLiveData<Int>()

    val finalRoundsPlayed: LiveData<WinnerType> get() = _finalRoundsPlayed
    private val _finalRoundsPlayed = MutableLiveData<WinnerType>()

    val gameResult: LiveData<JankenponResult?> get() = _gameResult
    private val _gameResult = MutableLiveData<JankenponResult?>()

    val pointsPlayer: LiveData<Int> get() = _pointsPlayer
    private val _pointsPlayer = MutableLiveData<Int>()

    val pointsOpponent: LiveData<Int> get() = _pointsOpponent
    private val _pointsOpponent = MutableLiveData<Int>()

    fun playGame(guess: String, player: String, opponent: String) {
        viewModelScope.launch {
            try {
                val response = repository.playGame(guess)
                _gameResult.value = response
                updatePlayerPoints(response.winner)
            } catch (e: Exception) {
                _gameResult.value = null
            }
        }
    }

    private fun updatePlayerPoints(winner: String) {
        when (winner.fromWinnerType()) {
            WinnerType.VICTORY -> {
                _pointsPlayer.value = _pointsPlayer.value?.plus(1)
                checkRoundsPlayed()
            }

            WinnerType.DEFEAT -> {
                _pointsOpponent.value = _pointsOpponent.value?.plus(1)
                checkRoundsPlayed()
            }

            else -> Unit
        }
    }

    fun savePlayerPoints(playerName: String) {
        playerDataManager.updatePlayerPoints(playerName)
    }

    private fun checkRoundsPlayed() {
        _roundsPlayed.value?.let { rounds ->
            _roundsPlayed.value = rounds.plus(1)
            Log.e("ROUNDS", rounds.toString())
            if (rounds >= 3) {
                checkRoundWinner()
                initRoundsPlayed()
            }
        }
    }

    private fun checkRoundWinner() {
        _pointsOpponent.value?.let { opponent ->
            _pointsPlayer.value?.let { player ->
                if (opponent > player) _finalRoundsPlayed.value = WinnerType.DEFEAT
                else _finalRoundsPlayed.value = WinnerType.VICTORY
            }
        }
    }

    fun initRoundsPlayed() {
        _roundsPlayed.value = 1
        _pointsOpponent.value = 0
        _pointsPlayer.value = 0
    }
}
