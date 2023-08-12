package org.example.delivery.http.handler.core.representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToWalletRequest(
    @SerialName("wallet_id") val walletId: String,
    @SerialName("card_id") val cardId: String,
    @SerialName("card_details") val cardDetails: CardDetailsRequest,
)

