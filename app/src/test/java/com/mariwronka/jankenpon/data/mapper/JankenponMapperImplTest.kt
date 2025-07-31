package com.mariwronka.jankenpon.data.mapper

import androidx.test.filters.SmallTest
import com.mariwronka.jankenpon.data.source.remote.entity.JankenponResponse
import com.mariwronka.jankenpon.domain.entity.JankenponType.PAPER
import com.mariwronka.jankenpon.domain.entity.JankenponType.ROCK
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@SmallTest
@RunWith(JUnit4::class)
class JankenponMapperImplTest {

    private val mapper = JankenponMapperImpl()

    @Test
    fun givenValidTags_whenMap_thenReturnCorrectJankenponResult() {
        val response = JankenponResponse(
            winner = "player",
            player = "paper",
            cpu = "rock",
            move = "paper beats rock",
        )

        val result = mapper.map(response)

        assertEquals("player", result.winner)
        assertEquals(PAPER, result.player)
        assertEquals(ROCK, result.cpu)
    }

    @Test
    fun givenInvalidTags_whenMap_thenReturnRockAsFallback() {
        val response = JankenponResponse(
            winner = "cpu",
            player = "banana",
            cpu = "abacate",
            move = "paper beats rock",
        )

        val result = mapper.map(response)

        assertEquals("cpu", result.winner)
        assertEquals(ROCK, result.player)
        assertEquals(ROCK, result.cpu)
    }
}
