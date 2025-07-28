package com.mariwronka.jankenpon.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mariwronka.jankenpon.databinding.ActivityHomeBinding
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

    private fun initObservers() {
        viewModel.opponentName.observe(this) { opponent ->
            viewModel.savePlayer(opponent)
            startActivity(
                Intent(this, GameActivity::class.java).apply {
                    putExtra("PLAYER_NAME", binding.tietPlayer.text.toString())
                    putExtra("OPPONENT_NAME", opponent)
                }
            )
        }
    }

    private fun setOnClickListeners() {
        binding.btStartGame.setOnClickListener {
            val playerName = binding.tietPlayer.text.toString()
            viewModel.savePlayer(playerName)
            viewModel.fetchOpponentName()
        }

        binding.btRanking.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }
    }
}
