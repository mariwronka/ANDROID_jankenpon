package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mariwronka.jankenpon.databinding.ActivityGameBinding
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import com.mariwronka.jankenpon.ui.common.BaseUiState.Loading
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSelectOptionJankenpon()
        setUpViewActions()
        setUpObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeLoading() }
                launch { observeWinner() }
                launch { observeResetEvent() }
            }
        }
    }

    private suspend fun observeLoading() {
        viewModel.baseUiState.collect { state ->
            binding.viewLoading.root.visibility = if (state is Loading) View.VISIBLE else View.GONE
        }
    }

    private suspend fun observeWinner() {
        viewModel.winnerType.collect { winner ->
            Toast.makeText(this, getString(winner.messageTextRes), Toast.LENGTH_SHORT).show()
            binding.selectOptionJankenpon.clearSelection()
        }
    }

    private suspend fun observeResetEvent() {
        viewModel.resetEvent.collect {
            binding.selectOptionJankenpon.clearSelection()
        }
    }

}
