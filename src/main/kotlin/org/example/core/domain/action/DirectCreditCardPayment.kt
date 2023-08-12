package org.example.core.domain.action

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.service.PaymentService

class DirectCreditCardPayment(
    private val paymentService: PaymentService
) {
    suspend operator fun invoke(actionData: ActionData) =
        with(actionData) {
            paymentService.creditCardPayment(cardDetails, amount)
        }

    data class ActionData(
        val cardDetails: CardDetails,
        val amount: Amount
    )
}