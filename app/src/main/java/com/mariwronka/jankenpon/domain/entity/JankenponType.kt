package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R

enum class JankenponType(
    val tag: String,
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val disabledIcon: Int
) {
    ROCK("rock", "Pedra", R.drawable.ic_rock_selected, R.drawable.ic_rock_disabled),
    PAPER("paper", "Papel", R.drawable.ic_paper_selected, R.drawable.ic_paper_disabled),
    SCISSORS("scissors", "Tesoura", R.drawable.ic_scissors_selected, R.drawable.ic_scissors_disabled);

    fun iconFor(selected: JankenponType): Int {
        return if (this == selected) selectedIcon else disabledIcon
    }

    companion object {
        fun String.fromJankenponType(): JankenponType? {
            return entries.find { it.tag == this }
        }

        fun List<JankenponType>.toChoiceOptions(): List<OptionsJankenpon> {
            return map { OptionsJankenpon(iconRes = it.selectedIcon, title = it.title, tag = it.tag) }
        }
    }
}

data class OptionsJankenpon(
    val iconRes: Int,
    val title: String,
    val tag: String
)


