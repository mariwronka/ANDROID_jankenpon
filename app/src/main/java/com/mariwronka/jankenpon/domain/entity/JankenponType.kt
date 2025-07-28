package com.mariwronka.jankenpon.domain.entity

enum class JankenponType(val tag: String) {
    ROCK(tag = "rock"), PAPER(tag = "paper"), SCISSORS(tag = "scissors");

    companion object {
        fun String.fromJankenponType(): JankenponType? {
            return entries.find { it.tag == this }
        }
    }
}
