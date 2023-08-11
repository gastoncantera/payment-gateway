package org.example.delivery.http.handler.core.representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDetailsRequest(
    @SerialName("holder_name") val holderName: String,
    @SerialName("card_number") val cardNumber: String,
    @SerialName("expiry_month") val expiryMonth: String,
    @SerialName("expiry_year") val expiryYear: String,
    @SerialName("security_code") val securityCode: String,
)