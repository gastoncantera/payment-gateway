package org.example.core.domain.infrastructure.service

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails

interface PaymentService {
    suspend fun getPaymentMethods(): String
    suspend fun creditCardPayment(cardDetails: CardDetails, amount: Amount): String
}