package com.mariwronka.jankenpon.data.mapper

import com.mariwronka.jankenpon.data.source.remote.entity.PlayerNameResponse
import com.mariwronka.jankenpon.domain.entity.PlayerName

object PlayerNameMapper {
    fun mapResponseToModel(response: PlayerNameResponse): PlayerName {
        return PlayerName(response.results)
    }
}
