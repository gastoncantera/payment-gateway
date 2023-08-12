package org.example.core.domain.action.wallet

import com.adyen.model.checkout.Amount
import org.example.core.domain.exception.WalletException
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository
import org.example.core.domain.infrastructure.service.AdyenPaymentService

class WalletCreditCardPayment(
    private val creditCardWalletRepository: CreditCardWalletRepository,
    private val adyenPaymentService: AdyenPaymentService
) {
    suspend operator fun invoke(actionData: ActionData) =
        with(actionData) {
            creditCardWalletRepository.get(walletId, cardId)?.let {
                adyenPaymentService.creditCardPayment(it.encryptedSecurityCode(securityCode), amount)
                    .resultCode.toString()
            } ?: throw WalletException("Could not find card details")
        }

    data class ActionData(
        val walletId: String,
        val cardId: String,
        val securityCode: String,
        val amount: Amount
    )
}