package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ActivityGameBinding
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.ui.common.base.BaseActivity
import com.mariwronka.jankenpon.ui.common.base.BaseUiState.Loading
import com.mariwronka.jankenpon.ui.common.extensions.gone
import com.mariwronka.jankenpon.ui.common.extensions.visible
import com.mariwronka.jankenpon.ui.common.extensions.visibleOrGone
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
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
            animateImageChange(binding.imageTop, ROCK.leftIcon)
            animateImageChange(binding.imageBottom, ROCK.rightIcon)
            startHandsLoopAnimation()
            binding.viewResultAlert.hide()
        }

        binding.viewResultAlert.onExit = {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpObservers() {
        launchRepeatOnLifecycle {
            launch { observeLoading() }
            launch { observeResult() }
            launch { observeResetEvent() }
        }
    }

    private suspend fun observeLoading() {
        viewModel.baseUiState.collect { state ->
            binding.viewLoading.root.visibleOrGone(state is Loading)
        }
    }

    private suspend fun observeResult() {
        viewModel.jankenponResult.collect { result ->
            stopHandsAnimationAndMeet()
            result.winner.fromWinnerType()?.let { winner ->
                binding.imageTop.setImageResource(result.cpu.leftIcon)
                binding.imageBottom.setImageResource(result.player.rightIcon)
                binding.selectOptionJankenpon.clearSelection()
                binding.viewResultAlert.show(winner)
            }
        }
    }

    private suspend fun observeResetEvent() {
        viewModel.resetEvent.collect {
            binding.selectOptionJankenpon.clearSelection()
        }
    }

    private fun stopHandsAnimationAndMeet() {
        binding.run {
            imageTop.clearAnimation()
            imageBottom.clearAnimation()

            applyAnimationTo(imageTop, R.anim.anim_slide_bottom_to_top)
            applyAnimationTo(imageBottom, R.anim.anim_slide_top_to_bottom)
        }
        binding.imageTop.clearAnimation()
        binding.imageBottom.clearAnimation()
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

    private fun animateImageChange(imageView: ImageView, @DrawableRes newRes: Int) {
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { /* do nothing */ }

            override fun onAnimationRepeat(animation: Animation?) { /* do nothing */ }

            override fun onAnimationEnd(animation: Animation?) {
                imageView.setImageResource(newRes)
                imageView.startAnimation(fadeIn)
            }
        })

        imageView.startAnimation(fadeOut)
    }
}
