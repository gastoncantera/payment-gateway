package org.example.core.domain.infrastructure.repository

import com.adyen.model.checkout.CardDetails

interface CreditCardWalletRepository {
    fun put(walletID: String, cardID: String, cardDetails: CardDetails)
    fun get(walletID: String, cardID: String): CardDetails?
}