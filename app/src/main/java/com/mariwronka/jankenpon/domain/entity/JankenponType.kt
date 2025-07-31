package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.R.drawable.ic_scissors_left
import com.mariwronka.jankenpon.R.drawable.ic_scissors_right
import com.mariwronka.jankenpon.R.drawable.ic_scissors_selected

enum class JankenponType(
    val tag: String,
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val leftIcon: Int,
    @DrawableRes val rightIcon: Int,
) {
    ROCK("rock", "Pedra", R.drawable.ic_rock_selected, R.drawable.ic_rock_left, R.drawable.ic_rock_right),
    PAPER("paper", "Papel", R.drawable.ic_paper_selected, R.drawable.ic_paper_left, R.drawable.ic_paper_right),
    SCISSORS("scissors", "Tesoura", ic_scissors_selected, ic_scissors_left, ic_scissors_right),
    ;

    companion object {
        fun List<JankenponType>.toChoiceOptions(): List<OptionsJankenpon> {
            return map { OptionsJankenpon(iconRes = it.selectedIcon, title = it.title, tag = it.tag) }
        }

        fun fromTag(tag: String?): JankenponType? = entries.find { it.tag == tag }
    }
}

data class OptionsJankenpon(
    val iconRes: Int,
    val title: String,
    val tag: String,
)
