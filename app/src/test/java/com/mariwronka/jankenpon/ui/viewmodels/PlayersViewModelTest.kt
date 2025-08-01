package com.mariwronka.jankenpon.ui.viewmodels

import com.mariwronka.jankenpon.common.BaseViewModelTest
import com.mariwronka.jankenpon.di.IO_DISPATCHER
import com.mariwronka.jankenpon.domain.repository.PlayersRepository
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.inject

class PlayersViewModelTest : BaseViewModelTest() {

    private val viewModel: PlayersViewModel by inject()

    @Before
    fun setup() {
        loadKoinModules(
            module {
                single<CoroutineDispatcher>(named(IO_DISPATCHER)) { Dispatchers.Unconfined }
                single { mockk<PlayersRepository>(relaxed = true) }
                single { PlayersViewModel(get()) }
            },
        )
    }

    @Test
    fun givenViewModel_whenInit_thenShouldBeInitialized() {
        assertNotNull(viewModel)
    }
}
