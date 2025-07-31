package com.mariwronka.jankenpon.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mariwronka.jankenpon.R

class PlayerScoreComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imageUser: ImageView
    private val textUser: TextView
    private val textPointUser: TextView

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

                if (hasValue(R.styleable.PlayerScoreView_victoryCount)) {
                    val victories = getInt(R.styleable.PlayerScoreView_victoryCount, 0)
                    setVictoryCount(victories)
                }
            } finally {
                recycle()
            }
        }
    }

    fun setVictoryCount(count: Int) {
        textPointUser.text = if (count == 0) {
            context.getString(R.string.no_victories)
        } else {
            resources.getQuantityString(R.plurals.player_score_count, count, count)
        }
    }
}
