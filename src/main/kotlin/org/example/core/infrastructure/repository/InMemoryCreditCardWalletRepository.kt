package org.example.core.infrastructure.repository

import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.repository.CreditCardWalletRepository

class InMemoryCreditCardWalletRepository : CreditCardWalletRepository {
    private var cards = mutableMapOf<String, CardDetails>()

    override fun put(walletID: String, cardID: String, cardDetails: CardDetails) {
        this.cards["$walletID-$cardID"] = cardDetails
    }

    override fun get(walletID: String, cardID: String): CardDetails? {
        return cards["$walletID-$cardID"]
    }
}