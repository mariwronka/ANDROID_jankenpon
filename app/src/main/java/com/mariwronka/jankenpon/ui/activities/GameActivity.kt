package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ActivityGameBinding
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import com.mariwronka.jankenpon.ui.common.base.BaseActivity
import com.mariwronka.jankenpon.ui.common.base.BaseUiState.Loading
import com.mariwronka.jankenpon.ui.common.extensions.visibleOrGone
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : BaseActivity<ActivityGameBinding>() {

    private val viewModel: PlayersViewModel by viewModel()

    override fun bind() = ActivityGameBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSelectOptionJankenpon()
        setUpViewActions()
        setUpObservers()
        setUpApplyAnimation()
    }

    private fun setUpSelectOptionJankenpon() {
        binding.selectOptionJankenpon.setOptions(JankenponType.entries.toChoiceOptions())
        binding.selectOptionJankenpon.onOptionSelected = { tag ->
            viewModel.playGame(tag)
        }
    }

    private fun setUpViewActions() {
        binding.buttonStop.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpObservers() {
        launchRepeatOnLifecycle {
            launch { observeLoading() }
            launch { observeWinner() }
            launch { observeResetEvent() }
        }
    }

    private suspend fun observeLoading() {
        viewModel.baseUiState.collect { state ->
            binding.viewLoading.root.visibleOrGone(state is Loading)
        }
    }

    private suspend fun observeWinner() {
        viewModel.winnerType.collect {
            binding.selectOptionJankenpon.clearSelection()
        }
    }

    private suspend fun observeResetEvent() {
        viewModel.resetEvent.collect {
            binding.selectOptionJankenpon.clearSelection()
        }
    }

    private fun setUpApplyAnimation() {
        binding.run {
            applyAnimationTo(imageTop, R.anim.anim_slide_bottom_to_top)
            applyAnimationTo(imageBottom, R.anim.anim_slide_top_to_bottom)
        }
    }
}
