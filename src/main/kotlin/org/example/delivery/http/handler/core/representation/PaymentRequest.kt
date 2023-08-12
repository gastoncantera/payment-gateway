package org.example.delivery.http.handler.core.representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    @SerialName("card_details") val cardDetails: CardDetailsRequest,
    @SerialName("amount") val amount: AmountRequest,
)