package com.mariwronka.jankenpon.data.mapper

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult

object JankenponMapper {
    fun mapResponseToModel(response: JankenponResponse): JankenponResult {
        return JankenponResult(
            cpu = response.cpu,
            player = response.player.orEmpty(),
            winner = response.winner,
            move = response.move.orEmpty(),
        )
    }
}
