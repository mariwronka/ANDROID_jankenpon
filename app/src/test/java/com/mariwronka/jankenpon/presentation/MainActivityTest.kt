package com.mariwronka.jankenpon.presentation

import com.mariwronka.jankenpon.common.BaseActivityTest
import com.mariwronka.jankenpon.common.TestConstants.INSTRUMENTED_PACKAGE
import com.mariwronka.jankenpon.common.TestConstants.SCREEN_SIZE
import com.mariwronka.jankenpon.common.TestConstants.SDK
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(
    sdk = [SDK],
    instrumentedPackages = [INSTRUMENTED_PACKAGE],
    qualifiers = SCREEN_SIZE,
)
@RunWith(RobolectricTestRunner::class)
class MainActivityTest : BaseActivityTest() {

    override fun provideModules(): List<Module> = listOf()

    @Test
    fun givenAppIsStarted_whenMainActivityIsLaunched_thenItShouldBeCreatedSuccessfully() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        val activity = controller.get()
        assertEquals(MainActivity::class.java, activity::class.java)
    }
}
