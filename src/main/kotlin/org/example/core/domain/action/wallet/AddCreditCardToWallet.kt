package org.example.core.domain.action.wallet

import com.adyen.model.checkout.CardDetails
import org.example.core.domain.exception.CardDetailsException
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository
import org.example.core.domain.model.ultis.Utils.isValid

class AddCreditCardToWallet(
    private val creditCardWalletRepository: CreditCardWalletRepository
) {
    operator fun invoke(actionData: ActionData) =
        with(actionData) {
            if (cardDetails.isValid()) {
                creditCardWalletRepository.put(walletId, cardId, cardDetails)
            } else {
                throw CardDetailsException("Invalid card details")
            }
        }

    data class ActionData(
        val walletId: String,
        val cardId: String,
        val cardDetails: CardDetails
    )
}