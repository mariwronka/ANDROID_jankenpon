package com.mariwronka.jankenpon.common

import com.mariwronka.jankenpon.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
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

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.NONE)
        modules(
            module {
                single { dispatcherRule.testDispatcher }
            },
        )
    }

    protected fun tearDownKoin() {
        stopKoin()
    }
}
