package org.example.core.domain.action

import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository

class AddCreditCardToWallet(
    private val creditCardWalletRepository: CreditCardWalletRepository
) {
    operator fun invoke(walletID: String, cardID: String, cardDetails: CardDetails) =
        creditCardWalletRepository.put(walletID, cardID, cardDetails)
}