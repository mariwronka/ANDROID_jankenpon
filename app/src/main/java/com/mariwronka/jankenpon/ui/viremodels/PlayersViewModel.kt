package com.mariwronka.jankenpon.ui.viremodels

import com.mariwronka.jankenpon.data.source.local.PlayerData
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.mariwronka.jankenpon.ui.common.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher

class PlayersViewModel(
    ioDispatcher: CoroutineDispatcher,
    private val repository: PlayersRepository,
    private val playerDataManager: PlayerDataManager,
) : BaseViewModel<List<String>>(ioDispatcher) {

    fun fetchOpponentName() = launchDataLoad { repository.getPlayerName().results }

    fun savePlayer(player: String) {
        if (player.isNotEmpty()) playerDataManager.savePlayerData(PlayerData(player))
    }

    fun getPlayerList() = playerDataManager.getPlayerList().toList()
}
