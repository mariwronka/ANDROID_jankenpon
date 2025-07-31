package com.mariwronka.jankenpon.data.repository

import com.mariwronka.jankenpon.common.BaseRepositoryTest
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResult
import org.junit.Test
import org.koin.test.inject

class JankenponRepositoryImplTest : BaseRepositoryTest() {

    private val repository: JankenponRepository by inject()

    @Test
    fun givenValidGuess_whenPlayGameIsCalled_thenReturnMappedResult() {
        mockResponseAndAssertAsyncValue(
            jsonFileName = "json/jankenpon_success_response.json",
            expected = JankenponResult(
                cpu = "rock",
                player = "paper",
                winner = "player",
                move = "paper beats rock",
            ),
        ) {
            repository.playGame("paper")
        }
    }
}
