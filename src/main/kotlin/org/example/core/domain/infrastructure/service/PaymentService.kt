package org.example.core.domain.infrastructure.service

interface PaymentService {
    suspend fun getPaymentMethods(): String
}