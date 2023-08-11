package org.example.core.domain.action

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.example.core.domain.infrastructure.service.PaymentService
import kotlin.test.Test

@ExperimentalCoroutinesApi
class MakeCreditCardPaymentTest {

    private val service: PaymentService = mockk(relaxed = true)
    private val action = MakeCreditCardPayment(service)

    @Test
    fun `should call creditCardPayment from service`() = runTest {
        whenMakeCreditCardPaymentIsInvoked()

        thenServiceCreditCardPaymentIsCalled()
    }

    private suspend fun whenMakeCreditCardPaymentIsInvoked() {
        action(CARD_DETAILS, AMOUNT)
    }

    private fun thenServiceCreditCardPaymentIsCalled() {
        coVerify(exactly = 1) { service.creditCardPayment(CARD_DETAILS, AMOUNT) }
    }

    private companion object {
        val CARD_DETAILS: CardDetails = CardDetails()
            .holderName("John Smith")
            .encryptedCardNumber("test_4111111111111111")
            .encryptedExpiryMonth("test_03")
            .encryptedExpiryYear("test_2030")
            .encryptedSecurityCode("test_737")
        val AMOUNT: Amount = Amount()
            .currency("EUR").value(1000)
    }
}