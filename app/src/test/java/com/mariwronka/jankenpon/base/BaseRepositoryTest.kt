package com.mariwronka.jankenpon.base

import com.mariwronka.jankenpon.di.provideTestApiModule
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

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

    open fun onSetup() = Unit

    private fun readJson(path: String): String {
        return javaClass.classLoader?.getResource(path)?.readText()
            ?: error("Arquivo $path não encontrado")
    }

    private fun enqueueResponse(body: String, code: Int = 200) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(body)
        )
    }

    private fun enqueueJson(fileName: String, code: Int = 200) = enqueueResponse(readJson(fileName), code)

    private fun enqueueErrorJson(code: Int = 400) = enqueueResponse("""{"error":"Algo deu errado"}""", code)

    fun <T> mockResponseAndAssertAsyncValue(jsonFileName: String, expected: T, block: suspend () -> T) = runTest {
        enqueueJson(jsonFileName)
        kotlin.test.assertEquals(expected, block())
    }

    suspend fun assertAsyncError(code: Int = 400, block: suspend () -> Unit) {
        enqueueErrorJson(code)
        runCatching { block() }
            .onFailure { /* Add validation if needed */ }
            .onSuccess { error("Esperava erro HTTP $code") }
    }
}
