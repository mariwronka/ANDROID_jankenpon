package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mariwronka.jankenpon.R

enum class WinnerType(
    @StringRes val messageTextRes: Int,
    @DrawableRes val backgroundRes: Int,
    @ColorRes val buttonColorRes: Int,
    val tag: String,
) {
    VICTORY(
        R.string.activity_start_game_message_victory,
        R.drawable.bg_victory,
        R.color.button_victory,
        "player",
    ),
    DEFEAT(
        R.string.activity_start_game_message_defeat,
        R.drawable.bg_defeat,
        R.color.button_defeat,
        "cpu",
    ),
    DRAW(
        R.string.activity_start_game_message_draw,
        R.drawable.bg_draw,
        R.color.button_draw,
        "draw",
    ), ;

    companion object {
        fun String.fromWinnerType() = entries.find { it.tag == this }
    }
}
