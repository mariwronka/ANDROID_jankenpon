package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mariwronka.jankenpon.databinding.ActivityGameBinding
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import com.mariwronka.jankenpon.ui.viremodels.JankenponViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JankenponViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSelectOptionJankenpon()
        viewModel.initRoundsPlayed()
    }

    private fun setUpSelectOptionJankenpon() {
        binding.selectOptionJankenpon.setOptions(JankenponType.entries.toChoiceOptions())
        binding.selectOptionJankenpon.onOptionSelected = { tag ->
            // TODO: O que fazer quando uma opção ser selecionada
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //    private var extraNamePlayer: String? = null
//    private var extraNameOpponent: String? = null
//    private fun setUpTextViewNames() {
//        extraNamePlayer = intent.getStringExtra("PLAYER_NAME")
//        extraNameOpponent = intent.getStringExtra("OPPONENT_NAME")
//
//        binding.tvNamePlayer.text =
//            getString(R.string.activity_start_game_name_player, extraNamePlayer)
//        binding.textDescriptionOpponent.text =
//            getString(R.string.activity_start_game_name_opponent, extraNameOpponent)
//        binding.includeRankingView.textRankNameOpponentName.text = extraNameOpponent
//    }
//
//    private fun setOnClickListeners() {
//        binding.imageHandRock.setOnClickListener { onPlayerChoice(ROCK) }
//        binding.imageHandPaper.setOnClickListener { onPlayerChoice(PAPER) }
//        binding.imageHandScissors.setOnClickListener { onPlayerChoice(SCISSORS) }
//    }
//
//    private fun onPlayerChoice(choice: JankenponType) {
//        viewModel.playGame(choice.tag)
//        binding.imageHandRock.setImageResource(ROCK.iconFor(choice))
//        binding.imageHandPaper.setImageResource(PAPER.iconFor(choice))
//        binding.imageHandScissors.setImageResource(SCISSORS.iconFor(choice))
//    }
//
//    private fun observeGameState() {
//        lifecycleScope.launch {
//            viewModel.baseUiState.collectLatest { state ->
//                when (state) {
//                    is BaseUiState.Loading -> {
//                        // Se quiser, mostrar um loading
//                    }
//
//                    is BaseUiState.Success -> {
//                        renderGame(state.data)
//                    }
//
//                    is BaseUiState.Error -> {
//                        Toast.makeText(
//                            this@GameActivity,
//                            getString(R.string.activity_start_game_message_error),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    is BaseUiState.Idle -> Unit
//                }
//            }
//        }
//    }
//
//    private fun renderGame(state: GameState) = with(binding) {
//        includeRankingView.textRankNamePlayerPoints.text = state.playerPoints.toString()
//        includeRankingView.textRankNameOpponentPoints.text = state.opponentPoints.toString()
//
//        val opponentType = state.result.cpu.fromJankenponType() ?: ROCK
//        imageOpponentsChoice.setImageResource(opponentType.selectedIcon)
//
//        val roundWinner = state.roundWinner ?: WinnerType.DRAW
//        showResultMessage(roundWinner)
//
//        ivBgMessage.visibility = View.VISIBLE
//        textMessage.visibility = View.VISIBLE
//
//        if (state.finalRoundWinner != null) {
//            if (state.finalRoundWinner == WinnerType.VICTORY) {
//                viewModel.savePlayerPoints(extraNamePlayer.orEmpty())
//            } else if (state.finalRoundWinner == WinnerType.DEFEAT) {
//                viewModel.savePlayerPoints(extraNameOpponent.orEmpty())
//            }
//
//            resetGameUI()
//        }
//    }
//
//    private fun resetGameUI() = with(binding) {
//        imageOpponentsChoice.setImageResource(R.drawable.ic_none)
//        imageHandRock.setImageResource(R.drawable.ic_rock_selected)
//        imageHandPaper.setImageResource(R.drawable.ic_paper_selected)
//        imageHandScissors.setImageResource(R.drawable.ic_scissors_selected)
//        ivBgMessage.visibility = View.GONE
//        textMessage.visibility = View.GONE
//    }
//
//    private fun showResultMessage(winner: WinnerType) = with(binding) {
//        ivBgMessage.setImageResource(winner.backgroundRes)
//        textMessage.setText(winner.messageTextRes)
//        textMessage.setTextAppearance(winner.textAppearanceRes)
//    }
}
