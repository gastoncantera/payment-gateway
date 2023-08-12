package org.example.core.domain.action

import org.example.core.domain.infrastructure.service.AdyenPaymentService

class GetPaymentMethods(
    private val adyenPaymentService: AdyenPaymentService
) {
    suspend operator fun invoke(): String = adyenPaymentService.getPaymentMethods().toJson()
}