package com.mariwronka.jankenpon.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textview.MaterialTextView
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.fromJankenponType
import com.mariwronka.jankenpon.domain.entity.JankenponType.PAPER
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK
import com.mariwronka.jankenpon.domain.entity.JankenponType.SCISSORS
import com.mariwronka.jankenpon.domain.entity.WinnerType
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import com.mariwronka.jankenpon.ui.viremodels.JankenponViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private val viewModel: JankenponViewModel by viewModel()

    private lateinit var namePlayer: MaterialTextView
    private lateinit var descriptionOpponent: MaterialTextView
    private lateinit var imageOpponentsChoice: AppCompatImageView
    private lateinit var imageHandRock: AppCompatImageView
    private lateinit var imageHandPaper: AppCompatImageView
    private lateinit var imageHandScissors: AppCompatImageView
    private lateinit var ivBgMessage: AppCompatImageView
    private lateinit var textMessage: MaterialTextView
    private lateinit var textRankNamePlayerName: AppCompatTextView
    private lateinit var textRankNamePlayerPoints: AppCompatTextView
    private lateinit var textRankNameOpponentName: AppCompatTextView
    private lateinit var textRankNameOpponentPoints: AppCompatTextView

    private var extraNamePlayer: String? = null
    private var extraNameOpponent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpFindViewByIds()
        setUpTextViewNames()
        initObserversViewModel()
        setOnClickListeners()
        viewModel.initRoundsPlayed()
    }

    private fun setUpTextViewNames() {
        extraNamePlayer = intent.getStringExtra("PLAYER_NAME")
        extraNameOpponent = intent.getStringExtra("OPPONENT_NAME")
        extraNamePlayer?.let { player ->
            namePlayer.text = getString(R.string.activity_start_game_name_player, player)
            textRankNamePlayerName.text = player
        }
        extraNameOpponent?.let { opponent ->
            descriptionOpponent.text = getString(R.string.activity_start_game_name_opponent, opponent)
            textRankNameOpponentName.text = opponent
        }
    }

    private fun initObserversViewModel() {
        viewModel.finalRoundsPlayed.observe(this) {
            if (it == WinnerType.VICTORY) viewModel.savePlayerPoints(extraNamePlayer.orEmpty())
            if (it == WinnerType.DEFEAT) viewModel.savePlayerPoints(extraNameOpponent.orEmpty())

            imageOpponentsChoice.setImageResource(R.drawable.ic_none)
            imageHandRock.setImageResource(R.drawable.ic_rock_selected)
            imageHandPaper.setImageResource(R.drawable.ic_paper_selected)
            imageHandScissors.setImageResource(R.drawable.ic_scissors_selected)

            ivBgMessage.visibility = View.GONE
            textMessage.visibility = View.GONE
        }

        viewModel.pointsPlayer.observe(this) { points ->
            textRankNamePlayerPoints.text = points.toString()
        }

        viewModel.pointsOpponent.observe(this) { points ->
            textRankNameOpponentPoints.text = points.toString()
        }

        viewModel.gameResult.observe(this) { result ->
            result?.let { result ->
                when (result.winner.fromWinnerType() ?: WinnerType.DRAW) {
                    WinnerType.VICTORY -> {
                        ivBgMessage.setImageResource(R.drawable.bg_message_victory)
                        textMessage.text = getString(R.string.activity_start_game_message_victory)
                        textMessage.setTextAppearance(R.style.TextViewMessageStyle_Victory)

                    }

                    WinnerType.DEFEAT -> {
                        ivBgMessage.setImageResource(R.drawable.bg_message_defeat)
                        textMessage.text = getString(R.string.activity_start_game_message_defeat)
                        textMessage.setTextAppearance(R.style.TextViewMessageStyle_Defeat)
                    }

                    WinnerType.DRAW -> {
                        ivBgMessage.setImageResource(R.drawable.bg_message_draw)
                        textMessage.text = getString(R.string.activity_start_game_message_draw)
                        textMessage.setTextAppearance(R.style.TextViewMessageStyle_Draw)
                    }
                }

                imageOpponentsChoice.setImageResource(
                    when (result.cpu.fromJankenponType() ?: ROCK) {
                        ROCK -> R.drawable.ic_rock_selected
                        PAPER -> R.drawable.ic_paper_selected
                        SCISSORS -> R.drawable.ic_scissors_selected
                    }
                )

                ivBgMessage.visibility = View.VISIBLE
                textMessage.visibility = View.VISIBLE
            } ?: run {
                Toast.makeText(
                    this,
                    getString(R.string.activity_start_game_message_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpFindViewByIds() {
        namePlayer = findViewById(R.id.tv_name_player)
        descriptionOpponent = findViewById(R.id.text_description_opponent)
        imageOpponentsChoice = findViewById(R.id.image_opponents_choice)
        imageHandRock = findViewById(R.id.image_hand_rock)
        imageHandPaper = findViewById(R.id.image_hand_paper)
        imageHandScissors = findViewById(R.id.image_hand_scissors)
        ivBgMessage = findViewById(R.id.iv_bg_message)
        textMessage = findViewById(R.id.text_message)
        textRankNamePlayerName = findViewById(R.id.text_rank_name_player_name)
        textRankNamePlayerPoints = findViewById(R.id.text_rank_name_player_points)
        textRankNameOpponentName = findViewById(R.id.text_rank_name_opponent_name)
        textRankNameOpponentPoints = findViewById(R.id.text_rank_name_opponent_points)
    }


    private fun setOnClickListeners() {
        imageHandRock.setOnClickListener { makeIconsRockPaperScissors(ROCK) }
        imageHandPaper.setOnClickListener { makeIconsRockPaperScissors(PAPER) }
        imageHandScissors.setOnClickListener { makeIconsRockPaperScissors(SCISSORS) }
    }

    private fun makeIconsRockPaperScissors(selectedOption: JankenponType) {
        viewModel.playGame(
            guess = selectedOption.tag,
            player = extraNamePlayer.orEmpty(),
            opponent = extraNameOpponent.orEmpty()
        )

        when (selectedOption) {
            ROCK -> {
                imageHandRock.setImageResource(R.drawable.ic_rock_selected)
                imageHandPaper.setImageResource(R.drawable.ic_paper_disabled)
                imageHandScissors.setImageResource(R.drawable.ic_scissors_disabled)
            }

            PAPER -> {
                imageHandRock.setImageResource(R.drawable.ic_rock_disabled)
                imageHandPaper.setImageResource(R.drawable.ic_paper_selected)
                imageHandScissors.setImageResource(R.drawable.ic_scissors_disabled)
            }

            SCISSORS -> {
                imageHandRock.setImageResource(R.drawable.ic_rock_disabled)
                imageHandPaper.setImageResource(R.drawable.ic_paper_disabled)
                imageHandScissors.setImageResource(R.drawable.ic_scissors_selected)

            }
        }
    }

}
