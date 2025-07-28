package com.mariwronka.jankenpon.ui.viremodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.data.source.local.PlayerData
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val repository: PlayersRepository,
    private val playerDataManager: PlayerDataManager,
) : ViewModel() {

    val opponentName: LiveData<String> get() = _opponentName
    private val _opponentName = MutableLiveData<String>()

    fun fetchOpponentName() {
        viewModelScope.launch {
            try {
                val response = repository.getPlayerName()
                _opponentName.value = response.results.first()
            } catch (e: Exception) {
                Log.i("ERROR API", e.toString())
            }
        }
    }

    fun savePlayer(player: String) {
        if (player.isNotEmpty()) playerDataManager.savePlayerData(PlayerData(player))
    }

    fun getPlayerList(): List<PlayerData> {
        return playerDataManager.getPlayerList().toList()
    }
}
