package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.data.source.local.PlayerDataManager
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import com.mariwronka.jankenpon.domain.entity.PlayerType.COMPUTER
import com.mariwronka.jankenpon.domain.entity.PlayerType.YOU
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: PlayersRepositoryImpl
    private lateinit var manager: PlayerDataManager

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        manager = mockk()
        repository = PlayersRepositoryImpl(
            api = mockk(relaxed = true),
            mapper = mockk(relaxed = true),
            manager = manager,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
    fun givenGuess_whenPlayGame_thenCallApiAndMapResponse() = runTest {
        val fakeResponse = mockk<JankenponResponse>()
        val expectedResult = mockk<JankenponResult>()
        val guess = "rock"

        coEvery { repository.api.playGame(guess) } returns fakeResponse
        every { repository.mapper.map(fakeResponse) } returns expectedResult

        val result = repository.playGame(guess)

        assertEquals(expectedResult, result)
        coVerify { repository.api.playGame(guess) }
        coVerify { repository.mapper.map(fakeResponse) }
    }

    @Test
    fun whenResetVictories_thenClearVictoryForBothPlayers() = runTest {
        coEvery { manager.clearVictoryCount(any()) } returns Unit

        repository.resetVictories()

        coVerify { manager.clearVictoryCount(YOU) }
        coVerify { manager.clearVictoryCount(COMPUTER) }
    }

    @Test
    fun givenPlayerTypeYOU_whenIncrementVictory_thenCallIncrementYouWins() = runTest {
        coEvery { manager.incrementYouWins() } returns 1

        repository.incrementVictory(YOU)

        coVerify { manager.incrementYouWins() }
        coVerify(exactly = 0) { manager.incrementComputerWins() }
    }

    @Test
    fun givenPlayerTypeCOMPUTER_whenIncrementVictory_thenCallIncrementComputerWins() = runTest {
        coEvery { manager.incrementComputerWins() } returns 1

        repository.incrementVictory(COMPUTER)

        coVerify { manager.incrementComputerWins() }
        coVerify(exactly = 0) { manager.incrementYouWins() }
    }

    @Test
    fun givenPlayerType_whenClearVictoryCount_thenDelegateToManager() = runTest {
        coEvery { manager.clearVictoryCount(YOU) } returns Unit
        repository.clearVictoryCount(YOU)
        coVerify { manager.clearVictoryCount(YOU) }
    }
}
