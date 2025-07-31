package com.mariwronka.jankenpon.data.source.remote.entity

import com.mariwronka.jankenpon.domain.entity.JankenponType

data class JankenponResult(
    val winner: String,
    val player: JankenponType,
    val cpu: JankenponType,
)
