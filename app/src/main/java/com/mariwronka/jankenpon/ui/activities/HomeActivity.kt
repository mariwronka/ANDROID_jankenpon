package com.mariwronka.jankenpon.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.databinding.ActivityHomeBinding
import com.mariwronka.jankenpon.ui.common.BaseUiState
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        setOnClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.postIdle()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.baseUiState.collect { state ->
                when (state) {
                    is BaseUiState.Idle -> binding.viewLoading.root.visibility = GONE

                    is BaseUiState.Loading -> binding.viewLoading.root.visibility = VISIBLE

                    is BaseUiState.Success -> {
                        val opponent = state.data.firstOrNull().orEmpty()
                        viewModel.savePlayer(opponent)
                        startActivity(
                            Intent(this@HomeActivity, GameActivity::class.java).apply {
                                putExtra("PLAYER_NAME", binding.viewLogin.tietPlayer.text.toString())
                                putExtra("OPPONENT_NAME", opponent)
                            },
                        )
                    }

                    is BaseUiState.Error -> {
                        Toast.makeText(
                            baseContext,
                            getString(R.string.activity_login_error),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.viewLogin.btStartGame.setOnClickListener {
            val playerName = binding.viewLogin.tietPlayer.text.toString()
            viewModel.savePlayer(playerName)
            viewModel.fetchOpponentName()
        }

        binding.viewLogin.btRanking.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }
    }
}
