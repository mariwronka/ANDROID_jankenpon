package com.mariwronka.jankenpon.domain.entity

import androidx.test.filters.SmallTest
import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@SmallTest
@RunWith(JUnit4::class)
class WinnerTypeTest {

    @Test
    fun givenValidTag_whenFromWinnerType_thenReturnCorrectEnum() {
        assertEquals(WinnerType.VICTORY, "player".fromWinnerType())
        assertEquals(WinnerType.DEFEAT, "cpu".fromWinnerType())
        assertEquals(WinnerType.DRAW, "draw".fromWinnerType())
    }

    @Test
    fun givenInvalidTag_whenFromWinnerType_thenReturnNull() {
        assertNull("invalid_tag".fromWinnerType())
        assertNull("".fromWinnerType())
    }

    @Test
    fun givenValidTags_whenFromWinnerType_thenReturnCorrectEnum() {
        assertEquals(WinnerType.VICTORY, "player".fromWinnerType())
        assertEquals(WinnerType.DEFEAT, "cpu".fromWinnerType())
        assertEquals(WinnerType.DRAW, "draw".fromWinnerType())
    }
}
