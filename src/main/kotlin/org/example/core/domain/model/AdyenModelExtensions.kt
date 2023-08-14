package org.example.core.domain.model

import com.adyen.model.checkout.CardDetails
import com.adyen.model.checkout.PaymentResponse
import org.example.core.domain.model.ultis.LuhnValidator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AdyenModelExtensions {
    fun CardDetails.isValid(): Boolean {
        val expiryMonth = this.encryptedExpiryMonth.removePrefix("test_")
        val expiryYear = this.encryptedExpiryYear.removePrefix("test_")
        try {
            if (SimpleDateFormat("MMyyyy").parse(expiryMonth + expiryYear).before(Date())) {
                return false
            }
        } catch (e: ParseException) {
            return false
        }

        val cardNumber = this.encryptedCardNumber.removePrefix("test_")
        return cardNumber.isNotEmpty() && LuhnValidator.isValid(cardNumber)
    }

    fun PaymentResponse.toSafeString(cardDetails: CardDetails? = null): String {
        val card = cardDetails?.encryptedCardNumber
            ?.removePrefix("test_")
            ?.takeLast(4)
            ?.padStart(cardDetails.encryptedCardNumber.length - 5, '*')
        val brand = if (paymentMethod == null) "" else paymentMethod.brand
        val amount = if (amount == null) "" else amount.currency.toString() + " " + amount.value
        val result = resultCode
        val refusalReasonRaw = additionalData["refusalReasonRaw"]

        return "CARD: $card | BRAND: $brand | AMOUNT: $amount | RESULT: $result ($refusalReasonRaw)"
    }
}