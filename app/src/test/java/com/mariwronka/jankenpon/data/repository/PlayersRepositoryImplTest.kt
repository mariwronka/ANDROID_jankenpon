package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.common.BaseRepositoryTest
import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.JankenponType
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.assertEquals

class PlayersRepositoryImplTest : BaseRepositoryTest() {

    private val repository: PlayersRepository by inject()
    private val manager: PlayerDataManager by inject()

    override fun onSetup() {
        loadKoinModules(
            module {
                single { mockk<PlayerDataManager>(relaxed = true) }
            },
        )
    }

    @Test
    fun givenVictoryCount_whenGetVictoryCount_thenReturnCorrectValueForYou() = runTest {
        every { manager.rawFlow } returns flowOf(Pair(5, 3))
        val result = repository.getVictoryCount(YOU)
        assertEquals(5, result.first())
    }

    @Test
    fun givenVictoryCount_whenGetVictoryCount_thenReturnCorrectValueForComputer() = runTest {
        every { manager.rawFlow } returns flowOf(Pair(5, 3))
        val result = repository.getVictoryCount(COMPUTER)
        assertEquals(3, result.first())
    }

    @Test
    fun whenResetVictories_thenClearVictoryForBothPlayers() = runTest {
        repository.resetVictories()
        coVerify { manager.clearVictoryCount(YOU) }
        coVerify { manager.clearVictoryCount(COMPUTER) }
    }

    @Test
    fun givenPlayerTypeYOU_whenIncrementVictory_thenCallIncrementYouWins() = runTest {
        repository.incrementVictory(YOU)
        coVerify { manager.incrementYouWins() }
        coVerify(exactly = 0) { manager.incrementComputerWins() }
    }

    @Test
    fun givenPlayerTypeCOMPUTER_whenIncrementVictory_thenCallIncrementComputerWins() = runTest {
        repository.incrementVictory(COMPUTER)
        coVerify { manager.incrementComputerWins() }
        coVerify(exactly = 0) { manager.incrementYouWins() }
    }

    @Test
    fun givenPlayerType_whenClearVictoryCount_thenDelegateToManager() = runTest {
        repository.clearVictoryCount(YOU)
        coVerify { manager.clearVictoryCount(YOU) }
    }

    @Test
    fun givenGuess_whenPlayGame_thenReturnExpectedResult() = runTest {
        mockResponseAndAssertAsyncValue(
            jsonFileName = "jankenpon_success.json",
            expected = JankenponResult(
                winner = "player",
                player = JankenponType.PAPER,
                cpu = JankenponType.ROCK,
            ),
        ) {
            repository.playGame("paper")
        }
    }

    @Test
    fun givenError500_whenPlayGame_thenThrowException() = runTest {
        assertAsyncError(500) {
            repository.playGame("rock")
        }
    }
}
