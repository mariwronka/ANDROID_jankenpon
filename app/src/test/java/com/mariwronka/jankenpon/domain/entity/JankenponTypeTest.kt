package com.mariwronka.jankenpon.domain.entity

import com.mariwronka.jankenpon.domain.entity.JankenponType.Companion.toChoiceOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class JankenponTypeTest {

    @Test
    fun givenValidTag_whenFromTag_thenReturnCorrectEnum() {
        assertEquals(JankenponType.ROCK, JankenponType.fromTag("rock"))
        assertEquals(JankenponType.PAPER, JankenponType.fromTag("paper"))
        assertEquals(JankenponType.SCISSORS, JankenponType.fromTag("scissors"))
    }

    @Test
    fun givenInvalidTag_whenFromTag_thenReturnNull() {
        assertNull(JankenponType.fromTag("banana"))
        assertNull(JankenponType.fromTag(null))
        assertNull(JankenponType.fromTag(""))
    }

    @Test
    fun givenListOfTypes_whenToChoiceOptions_thenReturnExpectedOptions() {
        val types = listOf(JankenponType.ROCK, JankenponType.PAPER, JankenponType.SCISSORS)
        val options = types.toChoiceOptions()

        assertEquals(3, options.size)
        assertEquals("Pedra", options[0].title)
        assertEquals("Papel", options[1].title)
        assertEquals("Tesoura", options[2].title)

        assertEquals("rock", options[0].tag)
        assertEquals("paper", options[1].tag)
        assertEquals("scissors", options[2].tag)

        assertEquals(JankenponType.ROCK.selectedIcon, options[0].iconRes)
    }

    @Test
    fun givenInvalidOrNullTag_whenFromTag_thenReturnNull() {
        assertNull(JankenponType.fromTag("invalid"))
        assertNull(JankenponType.fromTag(""))
        assertNull(JankenponType.fromTag(null))
    }

    @Test
    fun toChoiceOptions_shouldMapEnum() {
        val list = listOf(JankenponType.ROCK, JankenponType.PAPER, JankenponType.SCISSORS)
        val options = list.toChoiceOptions()
        assertEquals(3, options.size)
        assertEquals("Pedra", options[0].title)
    }

    @Test
    fun toChoiceOptions_shouldMapAllValuesCorrectly() {
        val options = listOf(
            JankenponType.ROCK,
            JankenponType.PAPER,
            JankenponType.SCISSORS,
        ).toChoiceOptions()

        assertEquals(3, options.size)
        assertEquals("Pedra", options[0].title)
        assertEquals("Papel", options[1].title)
        assertEquals("Tesoura", options[2].title)
    }
}
