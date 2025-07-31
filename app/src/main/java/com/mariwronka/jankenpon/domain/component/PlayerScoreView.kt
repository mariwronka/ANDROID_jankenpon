package com.mariwronka.jankenpon.domain.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mariwronka.jankenpon.R

class PlayerScoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imageUser: ImageView
    private val textUser: TextView
    private val textPointUser: TextView

    private var baseIcon: Int = R.drawable.ic_user_default

    enum class StatePlayer(val value: Int) {
        NORMAL(0), WIN(1);

        companion object {
            fun fromValue(value: Int): StatePlayer {
                return entries.firstOrNull { it.value == value } ?: NORMAL
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_player_score, this, true)

        imageUser = findViewById(R.id.imageUser)
        textUser = findViewById(R.id.textUser)
        textPointUser = findViewById(R.id.textPointUser)

        context.theme.obtainStyledAttributes(attrs, R.styleable.PlayerScoreView, 0, 0).apply {
            try {
                val playerType = getInt(R.styleable.PlayerScoreView_playerType, 0)

                imageUser.setImageResource(
                    if (playerType == 0) R.drawable.ic_user_default else R.drawable.ic_computer_default,
                )
                textUser.text = if (playerType == 0) "Você" else "Computador"

                val victories = getInt(R.styleable.PlayerScoreView_victoryCount, 0)
                setVictoryCount(victories)
            } finally {
                recycle()
            }
        }
    }

    fun setVictoryCount(count: Int) {
        textPointUser.text = resources.getQuantityString(R.plurals.player_score_count, count, count)
    }

    fun setPlayerType(isUser: Boolean) {
        // TODO: Mover pra string
        textUser.text = if (isUser) "Você" else "Computador"
    }

    fun setPlayerState(state: StatePlayer) {
        val drawableRes = when (state) {
            StatePlayer.NORMAL -> baseIcon
            StatePlayer.WIN -> getWinningIconFor(baseIcon)
        }
        imageUser.setImageResource(drawableRes)
    }

    private fun getWinningIconFor(defaultRes: Int): Int {
        val name = context.resources.getResourceEntryName(defaultRes)
        val winName = "${name}_win"
        val winResId = context.resources.getIdentifier(winName, "drawable", context.packageName)
        return if (winResId != 0) winResId else defaultRes
    }
}
