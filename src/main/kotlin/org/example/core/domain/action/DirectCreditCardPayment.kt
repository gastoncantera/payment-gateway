package org.example.core.domain.action

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import org.example.core.domain.infrastructure.service.AdyenPaymentService

class DirectCreditCardPayment(
    private val adyenPaymentService: AdyenPaymentService
) {
    suspend operator fun invoke(actionData: ActionData) =
        with(actionData) {
            adyenPaymentService.creditCardPayment(cardDetails, amount)
                .resultCode.toString()
        }

    data class ActionData(
        val cardDetails: CardDetails,
        val amount: Amount
    )
}