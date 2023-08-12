package org.example.core.domain.action

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.example.core.domain.infrastructure.service.AdyenPaymentService
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetPaymentMethodsTest {

    private val service: AdyenPaymentService = mockk(relaxed = true)
    private val action = GetPaymentMethods(service)

    @Test
    fun `should call getPaymentMethods from service`() = runTest {
        whenActionIsInvoked()

        thenServiceGetPaymentMethodsIsCalled()
    }

    private suspend fun whenActionIsInvoked() {
        action()
    }

    private fun thenServiceGetPaymentMethodsIsCalled() {
        coVerify(exactly = 1) { service.getPaymentMethods() }
    }
}