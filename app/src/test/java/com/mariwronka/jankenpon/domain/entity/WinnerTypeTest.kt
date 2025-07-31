package com.mariwronka.jankenpon.domain.entity

import com.mariwronka.jankenpon.domain.entity.WinnerType.Companion.fromWinnerType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

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
