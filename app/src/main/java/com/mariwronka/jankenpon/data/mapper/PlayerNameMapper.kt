package com.mariwronka.jankenpon.data.mapper

import com.mariwronka.jankenpon.data.source.remote.entity.PlayerNameResponse
import com.mariwronka.jankenpon.domain.entity.PlayerName

interface PlayerNameMapper {
    fun map(response: PlayerNameResponse): PlayerName
}

class PlayerNameMapperImpl : PlayerNameMapper {
    override fun map(response: PlayerNameResponse): PlayerName {
        return PlayerName(response.results)
    }
}
