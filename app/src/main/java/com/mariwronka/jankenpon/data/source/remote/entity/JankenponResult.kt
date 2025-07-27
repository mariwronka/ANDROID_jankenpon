package com.mariwronka.jankenpon.data.source.remote.entity

data class JankenponResult(
    val cpu: String,
    val player: String,
    val winner: String,
    val move: String,
)
