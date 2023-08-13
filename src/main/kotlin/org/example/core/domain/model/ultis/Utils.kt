package org.example.core.domain.model.ultis

import com.adyen.model.checkout.CardDetails
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
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
}