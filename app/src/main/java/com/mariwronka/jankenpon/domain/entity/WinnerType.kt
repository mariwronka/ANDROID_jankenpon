package com.mariwronka.jankenpon.domain.entity

import com.mariwronka.jankenpon.R

enum class WinnerType(val messageTextRes: Int, val tag: String) {
    VICTORY(R.string.activity_start_game_message_victory, "player"),
    DEFEAT(R.string.activity_start_game_message_defeat, "cpu"),
    DRAW(R.string.activity_start_game_message_draw, "draw"),
    ;

    companion object {
        fun String.fromWinnerType() = entries.find { it.tag == this }
    }
}
