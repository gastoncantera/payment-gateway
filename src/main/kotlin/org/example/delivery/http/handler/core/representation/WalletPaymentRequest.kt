package org.example.delivery.http.handler.core.representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletPaymentRequest(
    @SerialName("wallet_id") val walletId: String,
    @SerialName("card_id") val cardId: String,
    @SerialName("security_code") val securityCode: String,
    @SerialName("amount") val amount: AmountRequest,
)