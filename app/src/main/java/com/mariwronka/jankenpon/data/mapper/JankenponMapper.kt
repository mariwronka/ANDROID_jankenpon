package com.mariwronka.jankenpon.data.mapper

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult

interface JankenponMapper {
    fun map(response: JankenponResponse): JankenponResult
}

class JankenponMapperImpl : JankenponMapper {
    override fun map(response: JankenponResponse): JankenponResult {
        return JankenponResult(
            cpu = response.cpu,
            player = response.player.orEmpty(),
            winner = response.winner,
            move = response.move.orEmpty(),
        )
    }
}
