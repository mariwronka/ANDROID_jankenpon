package com.mariwronka.jankenpon.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.mariwronka.jankenpon.R.drawable.bg_option_selected
import com.mariwronka.jankenpon.R.drawable.bg_option_unselected
import com.mariwronka.jankenpon.R.drawable.ic_rock_selected
import com.mariwronka.jankenpon.databinding.ViewChoiceOptionItemBinding
import com.mariwronka.jankenpon.domain.entity.OptionsJankenpon

private const val PREVIEW_OPTION_COUNT = 3

class SelectOptionJankenponComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var selectedTag: String? = null
    private val optionViews = mutableListOf<View>()

    var onOptionSelected: ((tag: String) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        val previewOptions = (1..PREVIEW_OPTION_COUNT).map {
            OptionsJankenpon(
                ic_rock_selected,
                "Opção $it",
                "tag_$it",
            )
        }
        setOptions(previewOptions)
    }

    fun setOptions(options: List<OptionsJankenpon>) {
        removeAllViews()
        optionViews.clear()

        options.forEach { option ->
            ViewChoiceOptionItemBinding.inflate(LayoutInflater.from(context), this, false).let { binding ->
                binding.imageIcon.setImageResource(option.iconRes)
                binding.textTitle.text = option.title

                binding.root.setOnClickListener {
                    selectOption(option.tag)
                }

                binding.root.tag = option.tag
                optionViews.add(binding.root)
                addView(binding.root)
            }
        }
    }

    private fun selectOption(tag: String) {
        optionViews.forEach {
            it.setBackgroundResource(
                if (it.tag == tag) bg_option_selected else bg_option_unselected,
            )
        }
        selectedTag = tag
        onOptionSelected?.invoke(tag)
    }

    fun clearSelection() {
        selectedTag = null
        optionViews.forEach { it.setBackgroundResource(bg_option_unselected) }
    }
}
