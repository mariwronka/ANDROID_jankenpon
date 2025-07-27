package com.mariwronka.jankenpon.data.source.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PlayerDataManager(context: Context) {

    companion object {
        private const val MAX_TOP_PLAYERS = 8
    }

    private val sharedPreferences = context.getSharedPreferences("player_data", Context.MODE_PRIVATE)
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val type = Types.newParameterizedType(MutableList::class.java, PlayerData::class.java)
    private val adapter = moshi.adapter<MutableList<PlayerData>>(type)

    fun savePlayerData(playerData: PlayerData) {
        val playerList: MutableList<PlayerData> = getPlayerList()
        if (!playerList.any { it.playerName == playerData.playerName }) {
            playerList.add(playerData)
            savePlayerList(playerList)
        }
    }

    fun getPlayerList(): MutableList<PlayerData> {
        val json = sharedPreferences.getString("players", null)
        val list = json?.let { adapter.fromJson(it) } ?: mutableListOf()

        val sortedList = list.sortedByDescending { it.points }
        val topPlayers = sortedList.take(MAX_TOP_PLAYERS).toMutableList()

        sharedPreferences.edit().putString("players", adapter.toJson(topPlayers)).apply()
        return topPlayers
    }

    private fun savePlayerList(playerList: List<PlayerData>) {
        sharedPreferences.edit().putString("players", adapter.toJson(playerList.toMutableList())).apply()
    }

    fun getPlayerPoints(playerName: String): Int {
        return getPlayerList().find { it.playerName == playerName }?.points ?: 0
    }

    fun updatePlayerPoints(playerName: String) {
        val points = getPlayerPoints(playerName)
        deletePlayer(playerName)
        savePlayerData(PlayerData(playerName, points + 1))
    }

    private fun deletePlayer(playerName: String) {
        val playerList = getPlayerList()
        playerList.removeIf { it.playerName == playerName }
        savePlayerList(playerList)
    }
}
