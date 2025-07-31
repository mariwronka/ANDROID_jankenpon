package com.mariwronka.jankenpon.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.playerDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "player_prefs",
)

private object Keys {
    val YOU_WINS = intPreferencesKey("you_wins")
    val COMPUTER_WINS = intPreferencesKey("computer_wins")
}

class PlayerDataManager(context: Context) {
    private val ds = context.playerDataStore

    val rawFlow: Flow<Pair<Int, Int>> = ds.data
        .map { prefs ->
            val you = prefs[Keys.YOU_WINS] ?: 0
            val comp = prefs[Keys.COMPUTER_WINS] ?: 0
            you to comp
        }

    suspend fun incrementYouWins(): Int {
        var newValue = 0
        ds.edit { prefs ->
            val current = prefs[Keys.YOU_WINS] ?: 0
            newValue = current + 1
            prefs[Keys.YOU_WINS] = newValue
        }
        return newValue
    }

    suspend fun incrementComputerWins(): Int {
        var newValue = 0
        ds.edit { prefs ->
            val current = prefs[Keys.COMPUTER_WINS] ?: 0
            newValue = current + 1
            prefs[Keys.COMPUTER_WINS] = newValue
        }
        return newValue
    }
}
