package com.mariwronka.jankenpon.ui.viewmodels

import app.cash.turbine.test
import com.mariwronka.jankenpon.common.BaseViewModelTest
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.JankenponType.PAPER
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersViewModelTest : BaseViewModelTest() {

    override val koinTestModule = module { single<PlayersRepository> { mockk(relaxed = true) } }

    private val repository: PlayersRepository by inject()
    private val viewModel: PlayersViewModel by lazy { PlayersViewModel(repository) }

    @Before
    fun setup() {
        every { repository.getVictoryCount(YOU) } returns flowOf(7)
        every { repository.getVictoryCount(COMPUTER) } returns flowOf(4)
    }

    @Test
    fun givenGuessVictory_whenPlayGame_thenEmitResultAndIncrementYou() = runTest {
        val result = JankenponResult("player", PAPER, ROCK)
        coEvery { repository.playGame("paper") } returns result
        coEvery { repository.incrementVictory(YOU) } returns Unit

        viewModel.jankenponResult.test {
            viewModel.playGame("paper")
            assertEquals(result, awaitItem())
        }

        coVerify { repository.incrementVictory(YOU) }
    }

    @Test
    fun givenGuessDefeat_whenPlayGame_thenIncrementComputer() = runTest {
        val result = JankenponResult("cpu", ROCK, PAPER)
        coEvery { repository.playGame("rock") } returns result
        coEvery { repository.incrementVictory(COMPUTER) } returns Unit

        viewModel.jankenponResult.test {
            viewModel.playGame("rock")
            assertEquals(result, awaitItem())
        }

        coVerify { repository.incrementVictory(COMPUTER) }
    }

    @Test
    fun givenGuessDraw_whenPlayGame_thenNoVictoryIncremented() = runTest {
        val result = JankenponResult("draw", PAPER, PAPER)
        coEvery { repository.playGame("paper") } returns result

        viewModel.jankenponResult.test {
            viewModel.playGame("paper")
            assertEquals(result, awaitItem())
        }

        coVerify(exactly = 0) { repository.incrementVictory(any()) }
    }

    @Test
    fun whenResetGame_thenVictoryCountsAreCleared() = runTest {
        coEvery { repository.clearVictoryCount(YOU) } returns Unit
        coEvery { repository.clearVictoryCount(COMPUTER) } returns Unit

        viewModel.resetGame()
        advanceUntilIdle()

        coVerify { repository.clearVictoryCount(YOU) }
        coVerify { repository.clearVictoryCount(COMPUTER) }
    }

}
