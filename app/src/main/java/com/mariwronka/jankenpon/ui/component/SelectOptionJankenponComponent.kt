package com.mariwronka.jankenpon.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ViewChoiceOptionItemBinding
import com.mariwronka.jankenpon.domain.entity.OptionsJankenpon

class SelectOptionJankenponComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var selectedTag: String? = null
    private val optionViews = mutableListOf<View>()

    var onOptionSelected: ((tag: String) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        val previewOptions = (1..3).map { OptionsJankenpon(R.drawable.ic_rock_selected, "Opção $it", "tag_$it") }
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
                    selectOption(option.tag, binding.root)
                }

                binding.root.tag = option.tag
                optionViews.add(binding.root)
                addView(binding.root)
            }
        }
    }

    private fun selectOption(tag: String, clickedView: View) {
        optionViews.forEach {
            val isSelected = it.tag == tag
            it.setBackgroundResource(
                if (isSelected) R.drawable.bg_option_selected
                else R.drawable.bg_option_unselected
            )
        }
        selectedTag = tag
        onOptionSelected?.invoke(tag)
    }

    fun getSelected(): String? = selectedTag

    fun clearSelection() {
        selectedTag = null
        optionViews.forEach {
            it.setBackgroundResource(R.drawable.bg_option_unselected)
        }
    }
}
