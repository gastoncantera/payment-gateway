package org.example.core.domain.infrastructure.service

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import com.adyen.model.checkout.PaymentMethodsResponse
import com.adyen.model.checkout.PaymentResponse

interface AdyenPaymentService {
    suspend fun getPaymentMethods(): PaymentMethodsResponse
    suspend fun creditCardPayment(cardDetails: CardDetails, amount: Amount): PaymentResponse
}