package org.example.core.domain.action.wallet

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.example.core.domain.exception.WalletException
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository
import org.example.core.domain.infrastructure.service.AdyenPaymentService
import java.lang.Exception
import kotlin.test.Test

@ExperimentalCoroutinesApi
class WalletCreditCardPaymentTest {

    private val repository: CreditCardWalletRepository = mockk(relaxed = true)
    private val service: AdyenPaymentService = mockk(relaxed = true)
    private val action = WalletCreditCardPayment(repository, service)

    private lateinit var exception: Exception

    @Test
    fun `should call creditCardPayment from service if card details exists`() = runTest {
        givenCardDetailsInRepository()

        whenActionIsInvoked()

        thenServiceCreditCardPaymentIsCalled()
    }

    @Test
    fun `should throw exception if card details do not exist`() = runTest {
        givenNoCardDetailsInRepository()

        runCatching {
            whenActionIsInvoked()
        }.onFailure {
            exception = it as Exception
        }

        thenActionThrowsWalletException()
    }

    private fun givenCardDetailsInRepository() {
        every { repository.get(WALLET_ID, CARD_ID) } returns CARD_DETAILS
    }

    private fun givenNoCardDetailsInRepository() {
        every { repository.get(WALLET_ID, CARD_ID) } returns null
    }

    private suspend fun whenActionIsInvoked() {
        action(ACTION_DATA)
    }

    private fun thenServiceCreditCardPaymentIsCalled() {
        coVerify(exactly = 1) { service.creditCardPayment(CARD_DETAILS_WITH_CSC, AMOUNT) }
    }

    private fun thenActionThrowsWalletException() {
        assert(exception is WalletException)
    }

    private companion object {
        const val WALLET_ID = "1"
        const val CARD_ID = "1"
        const val SECURITY_CODE = "test_737"
        val AMOUNT: Amount = Amount()
            .currency("EUR").value(1000)
        var ACTION_DATA = WalletCreditCardPayment.ActionData(WALLET_ID, CARD_ID, SECURITY_CODE, AMOUNT)
        val CARD_DETAILS: CardDetails = CardDetails()
            .holderName("John Smith")
            .encryptedCardNumber("test_4111111111111111")
            .encryptedExpiryMonth("test_03")
            .encryptedExpiryYear("test_2030")
            .encryptedSecurityCode(null)
        val CARD_DETAILS_WITH_CSC: CardDetails = CARD_DETAILS.encryptedSecurityCode(SECURITY_CODE)
    }
}