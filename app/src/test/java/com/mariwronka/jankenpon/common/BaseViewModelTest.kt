package com.mariwronka.jankenpon.common

import com.mariwronka.jankenpon.di.IO_DISPATCHER
import com.mariwronka.jankenpon.rules.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

/**
 * Classe base para testes de ViewModel que utilizam coroutines e injeção de dependência com Koin.
 *
 * Essa classe configura duas regras:
 * - [MainDispatcherRule] para controlar o dispatcher principal durante testes com coroutines;
 * - [KoinTestRule] para iniciar o Koin com um módulo que injeta o dispatcher de teste.
 *
 * Ao herdar desta classe, seus testes de ViewModel terão um ambiente de coroutines controlado e
 * uma configuração básica de Koin pronta para uso.
 *
 * Exemplo de uso:
 * ```
 * class MyViewModelTest : BaseViewModelTest() {
 *     // seus testes aqui
 * }
 * ```
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModelTest : KoinTest {

    protected open val koinTestModule = module { }

    private val commonTestModule = module {
        single<CoroutineDispatcher> { dispatcherRule.testDispatcher }
        single<CoroutineDispatcher>(named(IO_DISPATCHER)) { dispatcherRule.testDispatcher }
    }

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.NONE)
        modules(commonTestModule, koinTestModule)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }
}
