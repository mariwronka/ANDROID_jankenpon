package com.mariwronka.jankenpon.common

import org.junit.After
import org.junit.Before
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

/**
 * Classe base para testes de Activities utilizando Robolectric e Koin.
 *
 * Essa classe garante que o contexto do Koin seja corretamente iniciado
 * e finalizado antes e depois da execução de cada teste.
 *
 * Subclasses devem fornecer os módulos do Koin necessários para os testes
 * implementando o método [provideModules], e podem opcionalmente sobrescrever
 * [onSetup] para realizar lógica extra após a inicialização.
 *
 * Exemplo de uso:
 * ```
 * class MainActivityTest : BaseActivityTest() {
 *     override fun provideModules(): List<Module> = listOf(myTestModule)
 * }
 * ```
 */
abstract class BaseActivityTest : KoinTest {

    @Before
    fun setupBaseActivityTest() {
        GlobalContext.getOrNull()?.let { stopKoin() }

        val modules = provideModules()
        if (modules.isNotEmpty()) {
            startKoin {
                modules(modules)
            }
        }

        onSetup()
    }

    @After
    fun tearDownBaseActivityTest() {
        stopKoin()
    }

    /**
     * Deve ser implementado pelas subclasses para fornecer os módulos do Koin
     * utilizados durante os testes.
     *
     * @return uma lista de módulos do Koin.
     */
    abstract fun provideModules(): List<Module>

    /**
     * Pode ser sobrescrito para executar lógica adicional após o setup inicial.
     */
    open fun onSetup() = Unit
}
