package org.example.core.domain.action

import org.example.core.domain.infrastructure.service.PaymentService

class GetPaymentMethods(
    private val paymentService: PaymentService
) {
    suspend operator fun invoke() = paymentService.getPaymentMethods()
}