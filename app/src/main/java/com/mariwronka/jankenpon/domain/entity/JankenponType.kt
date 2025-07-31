package com.mariwronka.jankenpon.domain.entity

import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R

enum class JankenponType(
    val tag: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val disabledIcon: Int,
) {
    ROCK("rock", R.drawable.ic_rock_selected, R.drawable.ic_rock_disabled),
    PAPER("paper", R.drawable.ic_paper_selected, R.drawable.ic_paper_disabled),
    SCISSORS("scissors", R.drawable.ic_scissors_selected, R.drawable.ic_scissors_disabled),
    ;

    fun iconFor(selected: JankenponType): Int {
        return if (this == selected) selectedIcon else disabledIcon
    }

    companion object {
        fun String.fromJankenponType(): JankenponType? {
            return entries.find { it.tag == this }
        }
    }
}
