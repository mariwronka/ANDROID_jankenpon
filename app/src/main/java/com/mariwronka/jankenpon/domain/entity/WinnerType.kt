package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R

enum class WinnerType(
    @DrawableRes val backgroundRes: Int,
    val messageTextRes: Int,
    val textAppearanceRes: Int,
    val tag: String,
) {
    VICTORY(
        backgroundRes = R.drawable.bg_message_victory,
        messageTextRes = R.string.activity_start_game_message_victory,
        textAppearanceRes = R.style.TextViewMessageStyle_Victory,
        tag = "player",
    ),
    DEFEAT(
        backgroundRes = R.drawable.bg_message_defeat,
        messageTextRes = R.string.activity_start_game_message_defeat,
        textAppearanceRes = R.style.TextViewMessageStyle_Defeat,
        tag = "cpu",
    ),
    DRAW(
        backgroundRes = R.drawable.bg_message_draw,
        messageTextRes = R.string.activity_start_game_message_draw,
        textAppearanceRes = R.style.TextViewMessageStyle_Draw,
        tag = "draw",
    );

    companion object {
        fun String.fromWinnerType() = entries.find { it.tag == this }
    }
}
