package com.mariwronka.jankenpon.data.source.remote.entity

import com.squareup.moshi.Json

data class JankenponResponse(
    @Json(name = "cpu") val cpu: String,
    @Json(name = "player") val player: String?,
    @Json(name = "winner") val winner: String,
    @Json(name = "move") val move: String?,
)
