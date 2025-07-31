package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mariwronka.jankenpon.databinding.ActivityRankingBinding
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Criar base activity evitando lifecycleScope na view
class RankingActivity : AppCompatActivity() {

    private var _binding: ActivityRankingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()
        observeScores()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun configureToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeScores() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.youWins.collect { count ->
                    binding.playerUser.setVictoryCount(count)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.computerWins.collect { count ->
                    binding.playerComputer.setVictoryCount(count)
                }
            }
        }
    }
}
