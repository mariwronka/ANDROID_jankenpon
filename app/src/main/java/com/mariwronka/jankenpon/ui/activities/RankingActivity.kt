package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ActivityRankingBinding
import com.mariwronka.jankenpon.ui.common.BaseActivity
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankingActivity : BaseActivity<ActivityRankingBinding>() {

    private val viewModel: PlayersViewModel by viewModel()

    override fun bind() = ActivityRankingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar(binding.toolbar, getString(R.string.rankin))
        configureResetGame()
        setUpObservers()
    }

    private fun configureResetGame() {
        binding.buttonRestart.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        viewModel.resetGame()
    }

    private fun setUpObservers() {
        launchRepeatOnLifecycle {
            launch {
                viewModel.youWins.collect {
                    binding.playerUser.setVictoryCount(it)
                }
            }
            launch {
                viewModel.computerWins.collect {
                    binding.playerComputer.setVictoryCount(it)
                }
            }
        }
    }
}
