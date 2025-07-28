package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariwronka.jankenpon.databinding.ActivityRankingBinding
import com.mariwronka.jankenpon.ui.adapters.RankingAdapter
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankingActivity : AppCompatActivity() {

    private var _binding: ActivityRankingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btRanking.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.activityListaProdutosRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RankingActivity)
            adapter = RankingAdapter(viewModel.getPlayerList())
        }
    }
}
