package org.example.core.domain.action.wallet

import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository

class AddCreditCardToWallet(
    private val creditCardWalletRepository: CreditCardWalletRepository
) {
    operator fun invoke(actionData: ActionData) =
        with(actionData) {
            creditCardWalletRepository.put(walletId, cardId, cardDetails)
        }

    data class ActionData(
        val walletId: String,
        val cardId: String,
        val cardDetails: CardDetails
    )
}