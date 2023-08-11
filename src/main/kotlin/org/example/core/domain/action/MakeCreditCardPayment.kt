package org.example.core.domain.action

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.service.PaymentService

class MakeCreditCardPayment(
    private val paymentService: PaymentService
) {
    suspend operator fun invoke(cardDetails: CardDetails, amount: Amount) =
        paymentService.creditCardPayment(cardDetails, amount)
}