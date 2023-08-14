package org.example.core.domain.action.wallet

import com.adyen.model.checkout.Amount
import org.example.core.domain.exception.WalletException
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository
import org.example.core.domain.infrastructure.service.AdyenPaymentService
import org.example.core.domain.model.AdyenModelExtensions.toSafeString
import org.slf4j.LoggerFactory

class WalletCreditCardPayment(
    private val creditCardWalletRepository: CreditCardWalletRepository,
    private val adyenPaymentService: AdyenPaymentService
) {
    private val logger = LoggerFactory.getLogger(WalletCreditCardPayment::class.java)

    suspend operator fun invoke(actionData: ActionData): String =
        with(actionData) {
            creditCardWalletRepository.get(walletId, cardId)?.let { cardDetails ->
                adyenPaymentService.creditCardPayment(cardDetails.encryptedSecurityCode(securityCode), amount).let {
                    logger.info("WALLET TRX: {}", it.toSafeString(cardDetails))
                    it.resultCode.toString()
                }
            } ?: throw WalletException("Could not find card details")
        }

    data class ActionData(
        val walletId: String,
        val cardId: String,
        val securityCode: String,
        val amount: Amount
    )
}