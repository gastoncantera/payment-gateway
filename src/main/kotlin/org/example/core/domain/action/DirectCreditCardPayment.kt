package org.example.core.domain.action

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import org.example.core.domain.exception.CardDetailsException
import org.example.core.domain.infrastructure.service.AdyenPaymentService
import org.example.core.domain.model.ultis.Utils.isValid
import org.example.core.domain.model.ultis.Utils.toSafeString
import org.slf4j.LoggerFactory

class DirectCreditCardPayment(
    private val adyenPaymentService: AdyenPaymentService
) {
    private val logger = LoggerFactory.getLogger(DirectCreditCardPayment::class.java)

    suspend operator fun invoke(actionData: ActionData) =
        with(actionData) {
            if (cardDetails.isValid()) {
                adyenPaymentService.creditCardPayment(cardDetails, amount).let {
                    logger.info("DIRECT TRX: {}", it.toSafeString(cardDetails))
                    it.resultCode.toString()
                }
            } else {
                throw CardDetailsException("Invalid card details")
            }
        }

    data class ActionData(
        val cardDetails: CardDetails,
        val amount: Amount
    )
}