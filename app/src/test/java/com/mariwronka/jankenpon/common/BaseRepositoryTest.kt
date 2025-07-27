package com.mariwronka.jankenpon.common

import com.mariwronka.jankenpon.di.provideTestApiModule
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * Classe base para testes de repositórios com MockWebServer.
 *
 * Essa classe configura o servidor local para interceptar chamadas HTTP e injetar
 * os módulos de teste via Koin. Também provê métodos utilitários para ler JSON,
 * mockar respostas e validar erros de forma simplificada.
 *
 * Exemplo de uso:
 * ```
 * class MeuRepositoryTest : BaseRepositoryTest() {
 *     override fun onSetup() {
 *         // Configuração adicional se necessário
 *     }
 * }
 * ```
 */
abstract class BaseRepositoryTest : KoinTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setupBase() {
        mockWebServer = MockWebServer().apply { start(8055) }
        startKoin { modules(provideTestApiModule(mockWebServer)) }
        onSetup()
    }

    @After
    fun tearDownBase() {
        mockWebServer.shutdown()
        stopKoin()
    }

    /**
     * Substitua para configurar algo adicional após o setup principal.
     */
    open fun onSetup() = Unit

    private fun readJson(path: String): String {
        return javaClass.classLoader?.getResource(path)?.readText()
            ?: error("Arquivo $path não encontrado")
    }

    private fun enqueueResponse(body: String, code: Int = 200) {
        mockWebServer.enqueue(MockResponse().setResponseCode(code).setBody(body))
    }

    private fun enqueueJson(fileName: String, code: Int = 200) {
        enqueueResponse(readJson(fileName), code)
    }

    private fun enqueueErrorJson(code: Int = 400) {
        enqueueResponse("""{"error":"Algo deu errado"}""", code)
    }

    /**
     * Executa uma chamada assíncrona e valida o valor retornado com base no mock enfileirado.
     *
     * @param jsonFileName nome do arquivo JSON a ser usado na resposta mockada.
     * @param expected valor esperado.
     * @param block função que executa a chamada a ser testada.
     */
    fun <T> mockResponseAndAssertAsyncValue(jsonFileName: String, expected: T, block: suspend () -> T) = runTest {
        enqueueJson(jsonFileName)
        kotlin.test.assertEquals(expected, block())
    }

    /**
     * Executa uma chamada assíncrona esperando erro e valida que realmente ocorreu.
     *
     * @param code status HTTP de erro esperado.
     * @param block função que executa a chamada a ser testada.
     */
    suspend fun assertAsyncError(code: Int = 400, block: suspend () -> Unit) {
        enqueueErrorJson(code)
        runCatching { block() }
            .onFailure { /* Adicione validações se necessário */ }
            .onSuccess { error("Esperava erro HTTP $code") }
    }
}
