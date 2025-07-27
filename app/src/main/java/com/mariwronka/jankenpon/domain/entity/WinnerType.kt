package com.mariwronka.jankenpon.domain.entity

enum class WinnerType(val tag: String) {
    VICTORY(tag = "player"), DEFEAT(tag = "cpu"), DRAW(tag = "draw");

    companion object {
        fun String.fromWinnerType(): WinnerType? {
            return entries.find { it.tag == this }
        }
    }
}
