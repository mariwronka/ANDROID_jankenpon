package com.mariwronka.jankenpon.common.extension

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Testa um Flow usando a API do Turbine com configuração padronizada.
 *
 * Exemplo:
 * flow.testWithTurbine {
 *     assertEquals(expected, awaitItem())
 * }
 */
suspend fun <T> Flow<T>.testWithTurbine(
    timeout: Duration = 1000.milliseconds,
    block: suspend TurbineTestContext<T>.() -> Unit,
) {
    this.test(timeout = timeout, validate = block)
}
