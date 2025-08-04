package com.mariwronka.jankenpon.ui.viewmodels

import app.cash.turbine.test
import com.mariwronka.jankenpon.common.BaseViewModelTest
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.PlayerType
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.mariwronka.jankenpon.ui.common.base.BaseUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import org.koin.dsl.module

const val MOCK_PLAYER = "player"
const val MOCK_CPU = "cpu"
const val MOCK_PAPER = "paper"
const val MOCK_ROCK = "rock"
const val MOCK_DRAW = "draw"
val MOCK_EXCEPTION = RuntimeException("Algo deu errado")

class PlayersViewModelTest : BaseViewModelTest() {

    override val koinTestModule = module { single<PlayersRepository> { mockk(relaxed = true) } }

    private val repository: PlayersRepository by inject()
    private val viewModel: PlayersViewModel by lazy { PlayersViewModel(repository) }

    @Before
    fun setup() {
        every { repository.getVictoryCount(PlayerType.YOU) } returns flowOf(7)
        every { repository.getVictoryCount(PlayerType.COMPUTER) } returns flowOf(4)
    }

    @Test
    fun givenGuessVictory_whenPlayGame_thenEmitResultAndIncrementYou() = runTest {
        val result = JankenponResult(MOCK_PLAYER, JankenponType.PAPER, JankenponType.ROCK)
        coEvery { repository.playGame(MOCK_PAPER) } returns result
        coEvery { repository.incrementVictory(PlayerType.YOU) } returns Unit

        viewModel.baseUiState.test {
            viewModel.playGame(MOCK_PAPER)
            assertStandardUiFlow(BaseUiState.Success(result))
        }

        coVerify { repository.incrementVictory(PlayerType.YOU) }
    }

    @Test
    fun givenGuessDefeat_whenPlayGame_thenIncrementComputer() = runTest {
        val result = JankenponResult(MOCK_CPU, JankenponType.ROCK, JankenponType.PAPER)
        coEvery { repository.playGame(MOCK_ROCK) } returns result
        coEvery { repository.incrementVictory(PlayerType.COMPUTER) } returns Unit

        viewModel.baseUiState.test {
            viewModel.playGame(MOCK_ROCK)
            assertStandardUiFlow(BaseUiState.Success(result))
        }

        coVerify { repository.incrementVictory(PlayerType.COMPUTER) }
    }


    @Test
    fun givenGuessDraw_whenPlayGame_thenNoVictoryIncremented() = runTest {
        val result = JankenponResult(MOCK_DRAW, JankenponType.PAPER, JankenponType.PAPER)
        coEvery { repository.playGame(MOCK_PAPER) } returns result

        viewModel.baseUiState.test {
            viewModel.playGame(MOCK_PAPER)
            assertStandardUiFlow(BaseUiState.Success(result))
        }

        coVerify(exactly = 0) { repository.incrementVictory(any()) }
    }

    @Test
    fun whenResetGame_thenVictoryCountsAreCleared() = runTest {
        coEvery { repository.clearVictoryCount(PlayerType.YOU) } returns Unit
        coEvery { repository.clearVictoryCount(PlayerType.COMPUTER) } returns Unit

        viewModel.baseUiState.test {
            viewModel.resetGame()
            assertStandardUiFlow(BaseUiState.Success(null), showLoading = false)
        }

        coVerify { repository.clearVictoryCount(PlayerType.YOU) }
        coVerify { repository.clearVictoryCount(PlayerType.COMPUTER) }
    }

    @Test
    fun givenGuess_whenPlayGameFails_thenEmitErrorState() = runTest {
        coEvery { repository.playGame(any()) } throws MOCK_EXCEPTION

        viewModel.baseUiState.test {
            viewModel.playGame(MOCK_ROCK)
            assertStandardUiFlow(BaseUiState.Error(MOCK_EXCEPTION))
        }

        coVerify(exactly = 0) { repository.incrementVictory(any()) }
    }

}
