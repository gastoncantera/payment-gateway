package org.example.delivery.http.handler.core.representation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmountRequest(
    @SerialName("currency") val currency: String,
    @SerialName("value") val value: Long,
)