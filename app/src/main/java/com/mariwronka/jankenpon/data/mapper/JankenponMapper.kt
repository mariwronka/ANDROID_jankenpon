package com.mariwronka.jankenpon.data.mapper

import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.fromTag
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK

interface JankenponMapper {
    fun map(response: JankenponResponse): JankenponResult
}

class JankenponMapperImpl : JankenponMapper {
    override fun map(response: JankenponResponse): JankenponResult {
        return JankenponResult(
            winner = response.winner,
            player = fromTag(response.player) ?: ROCK,
            cpu = fromTag(response.cpu) ?: ROCK,
        )
    }
}
