package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ActivityGameBinding
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.ui.common.base.BaseActivity
import com.mariwronka.jankenpon.ui.common.base.BaseUiState
import com.mariwronka.jankenpon.ui.common.base.BaseUiState.Loading
import com.mariwronka.jankenpon.ui.common.extensions.gone
import com.mariwronka.jankenpon.ui.common.extensions.visible
import com.mariwronka.jankenpon.ui.common.extensions.visibleOrGone
import com.mariwronka.jankenpon.ui.viewmodels.PlayersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : BaseActivity<ActivityGameBinding>() {

    companion object {
        private const val DELAY_KEN_MS = 400L
        private const val DELAY_PON_MS = 800L
    }

    private val viewModel: PlayersViewModel by viewModel()

    override fun bind() = ActivityGameBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSelectOptionJankenpon()
        setUpViewActions()
        setUpObservers()
        startHandsLoopAnimation()
        setUpApplyTextViewAnimation()
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

        binding.viewResultAlert.onPlayAgain = {
            binding.imageTop.setImageResource(ROCK.leftIcon)
            binding.imageBottom.setImageResource(ROCK.rightIcon)
            startHandsLoopAnimation()
            binding.viewResultAlert.hide()
        }

        binding.viewResultAlert.onExit = {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpObservers() {
        launchRepeatOnLifecycle {
            launch {
                viewModel.baseUiState.collect { state ->
                    binding.viewLoading.root.visibleOrGone(state is Loading)

                    when (state) {
                        is BaseUiState.Success -> {
                            viewModel.postIdle()
                            state.data?.let { result ->
                                binding.imageTop.setImageResource(result.cpu.leftIcon)
                                binding.imageBottom.setImageResource(result.player.rightIcon)
                                binding.selectOptionJankenpon.clearSelection()
                                binding.viewResultAlert.show(result.winner.fromWinnerType())
                            }
                        }

                        is BaseUiState.Error -> {
                            viewModel.postIdle()
                            Toast.makeText(
                                this@GameActivity,
                                "Erro: ${state.throwable.message}",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun startHandsLoopAnimation() {
        binding.run {
            applyAnimationTo(imageTop, R.anim.anim_up_down_loop)
            applyAnimationTo(imageBottom, R.anim.anim_down_up_loop)
        }
    }

    private fun setUpApplyTextViewAnimation() {
        binding.run {
            viewTextAnimate.icJan.gone()
            viewTextAnimate.icKen.gone()
            viewTextAnimate.icPon.gone()

            viewTextAnimate.icJan.postDelayed({
                viewTextAnimate.icJan.visible()
                applyAnimationTo(viewTextAnimate.icJan, R.anim.anim_fade_in)
            }, 0)

            viewTextAnimate.icKen.postDelayed({
                viewTextAnimate.icKen.visible()
                applyAnimationTo(viewTextAnimate.icKen, R.anim.anim_fade_in)
            }, DELAY_KEN_MS)

            viewTextAnimate.icPon.postDelayed({
                viewTextAnimate.icPon.visible()
                applyAnimationTo(viewTextAnimate.icPon, R.anim.anim_fade_in)
            }, DELAY_PON_MS)
        }
    }
}
