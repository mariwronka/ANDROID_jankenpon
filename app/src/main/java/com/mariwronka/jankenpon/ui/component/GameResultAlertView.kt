package com.mariwronka.jankenpon.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import com.mariwronka.jankenpon.databinding.ViewGameResultAlertBinding
import com.mariwronka.jankenpon.databinding.ViewGameResultAlertBinding.inflate
import com.mariwronka.jankenpon.domain.entity.WinnerType
import com.mariwronka.jankenpon.ui.common.extensions.blockTouch
import com.mariwronka.jankenpon.ui.common.extensions.gone
import com.mariwronka.jankenpon.ui.common.extensions.visible

class GameResultAlertComponente @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding: ViewGameResultAlertBinding =
        inflate(LayoutInflater.from(context), this).also { it.root.gone() }

    var onPlayAgain: (() -> Unit)? = null
    var onExit: (() -> Unit)? = null

    init {
        binding.buttonPlayAgain.setOnClickListener { onPlayAgain?.invoke() }
        binding.buttonExit.setOnClickListener { onExit?.invoke() }
    }

    fun show(winnerType: WinnerType) {
        binding.root.setBackgroundResource(winnerType.backgroundRes)
        binding.textTitle.setText(winnerType.messageTextRes)
        binding.textTitle.setTextColor(getColor(context, winnerType.titleTextColorRes))
        binding.buttonPlayAgain.setBackgroundColor(getColor(context, winnerType.buttonColorRes))
        blockTouch()
        visible()
    }

    fun hide() {
        gone()
    }
}
