package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R

enum class JankenponType(val tag: String, val title: String, @DrawableRes val selectedIcon: Int) {
    ROCK("rock", "Pedra", R.drawable.ic_rock_selected),
    PAPER("paper", "Papel", R.drawable.ic_paper_selected),
    SCISSORS("scissors", "Tesoura", R.drawable.ic_scissors_selected),
    ;

    companion object {
        fun List<JankenponType>.toChoiceOptions(): List<OptionsJankenpon> {
            return map { OptionsJankenpon(iconRes = it.selectedIcon, title = it.title, tag = it.tag) }
        }
    }
}

data class OptionsJankenpon(
    val iconRes: Int,
    val title: String,
    val tag: String,
)
