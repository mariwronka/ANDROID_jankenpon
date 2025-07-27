package com.mariwronka.jankenpon.data.source.remote.entity

import com.squareup.moshi.Json

data class PlayerNameResponse(
    @Json(name = "results") val results: List<String>,
)
